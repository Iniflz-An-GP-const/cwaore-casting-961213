package gourmet.cwaorecasting.hubapp.ui.viewmodel

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gourmet.cwaorecasting.hubapp.data.model.BestScores
import gourmet.cwaorecasting.hubapp.data.model.CARD_SYMBOL_COUNT
import gourmet.cwaorecasting.hubapp.data.model.CardState
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.data.model.GamePhase
import gourmet.cwaorecasting.hubapp.data.model.GameUiState
import gourmet.cwaorecasting.hubapp.data.model.MemoryCard
import gourmet.cwaorecasting.hubapp.data.model.PauseReason
import gourmet.cwaorecasting.hubapp.data.repository.HighScoreRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.time.Duration.Companion.milliseconds

class GameViewModel(
    private val difficulty: Difficulty,
    private val highScoreRepository: HighScoreRepository,
) : ViewModel() {

    private val _state =
        MutableStateFlow(newGame(best = BestScores(bestMoves = null, bestTimeMs = null)))
    val state: StateFlow<GameUiState> = _state.asStateFlow()

    private var previewJob: Job? = null
    private var mismatchJob: Job? = null
    private var timerJob: Job? = null
    private var finishJob: Job? = null

    /** The first card of the pair being looked for; null when no card is waiting. */
    private var openedCardId: Int? = null
    private var isResolvingMismatch = false

    /** Time played in the finished stretches; the running one is added on top while the clock ticks. */
    private var accumulatedMs = 0L
    private var segmentStartedAtMs = 0L
    private var isTimerRunning = false
    private var previewRemainingMs = difficulty.previewDurationMs
    private var round = 0

    private val pauseReasons = mutableSetOf<PauseReason>()
    private val isPaused: Boolean get() = pauseReasons.isNotEmpty()

    init {
        startPreview()
        viewModelScope.launch {
            val best = highScoreRepository.getBest(difficulty)
            _state.update { it.copy(best = best) }
        }
    }

    fun onCardClick(cardId: Int) {
        val current = _state.value
        if (current.phase != GamePhase.Playing || isResolvingMismatch || isPaused) return

        val card = current.cards.first { it.id == cardId }
        if (card.state != CardState.Hidden) return

        val firstId = openedCardId
        if (firstId == null) {
            openedCardId = cardId
            _state.update { it.copy(cards = it.cards.withState(setOf(cardId), CardState.Revealed)) }
            return
        }

        openedCardId = null
        val first = current.cards.first { it.id == firstId }
        val pairIds = setOf(firstId, cardId)

        if (first.symbolIndex == card.symbolIndex) {
            val cards = current.cards.withState(pairIds, CardState.Matched)
            val moves = current.moves + 1
            _state.update { it.copy(cards = cards, moves = moves) }
            if (cards.all { it.state == CardState.Matched }) finishGame(moves)
        } else {
            isResolvingMismatch = true
            _state.update {
                it.copy(
                    cards = it.cards.withState(pairIds, CardState.Mismatch),
                    moves = it.moves + 1,
                )
            }
            mismatchJob = viewModelScope.launch {
                delay(MISMATCH_DELAY_MS.milliseconds)
                _state.update { it.copy(cards = it.cards.withState(pairIds, CardState.Hidden)) }
                isResolvingMismatch = false
            }
        }
    }

    fun setPaused(reason: PauseReason, paused: Boolean) {
        val wasPaused = isPaused
        if (paused) pauseReasons.add(reason) else pauseReasons.remove(reason)
        if (isPaused == wasPaused) return

        if (isPaused) {
            previewJob?.cancel()
            stopTimer()
        } else {
            when (_state.value.phase) {
                GamePhase.Preview -> startPreview()
                GamePhase.Playing -> startTimer()
                GamePhase.Finished -> Unit
            }
        }
    }

    fun restart() {
        previewJob?.cancel()
        mismatchJob?.cancel()
        finishJob?.cancel()
        timerJob?.cancel()
        isTimerRunning = false
        accumulatedMs = 0L
        openedCardId = null
        isResolvingMismatch = false
        round++
        previewRemainingMs = difficulty.previewDurationMs
        _state.value = newGame(best = _state.value.best)
        if (!isPaused) startPreview()
    }

    /**
     * Counts the preview down by the clock, a few times a second, so the label and the bar in the
     * panel move together. The time left is kept in milliseconds, so after a pause the preview
     * continues exactly where it stopped.
     */
    private fun startPreview() {
        previewJob = viewModelScope.launch {
            val deadlineMs = SystemClock.elapsedRealtime() + previewRemainingMs

            while (true) {
                previewRemainingMs = (deadlineMs - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
                _state.update {
                    it.copy(
                        previewSecondsLeft = ceil(previewRemainingMs / 1_000f).toInt(),
                        previewFraction = previewRemainingMs.toFloat() / difficulty.previewDurationMs,
                    )
                }
                if (previewRemainingMs == 0L) break
                delay(PREVIEW_TICK_MS.milliseconds)
            }

            _state.update {
                it.copy(
                    cards = it.cards.map { card -> card.copy(state = CardState.Hidden) },
                    phase = GamePhase.Playing,
                )
            }
            startTimer()
        }
    }

    private fun currentElapsedMs(): Long =
        accumulatedMs + if (isTimerRunning) SystemClock.elapsedRealtime() - segmentStartedAtMs else 0L

    private fun startTimer() {
        if (isTimerRunning) return

        isTimerRunning = true
        segmentStartedAtMs = SystemClock.elapsedRealtime()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(TIMER_TICK_MS.milliseconds)
                _state.update { it.copy(elapsedMs = currentElapsedMs()) }
            }
        }
    }

    private fun stopTimer() {
        if (!isTimerRunning) return

        accumulatedMs = currentElapsedMs()
        isTimerRunning = false
        timerJob?.cancel()
        _state.update { it.copy(elapsedMs = accumulatedMs) }
    }

    private fun finishGame(moves: Int) {
        stopTimer()
        val timeMs = accumulatedMs

        finishJob = viewModelScope.launch {
            val result = highScoreRepository.submit(difficulty, moves, timeMs)
            delay(FINISH_DIALOG_DELAY_MS.milliseconds)
            _state.update {
                it.copy(
                    phase = GamePhase.Finished,
                    result = result,
                    best = result.best
                )
            }
        }
    }

    private fun newGame(best: BestScores): GameUiState {
        val symbols = (0 until CARD_SYMBOL_COUNT).shuffled().take(difficulty.pairCount)
        val cards = (symbols + symbols)
            .shuffled()
            .mapIndexed { index, symbol ->
                MemoryCard(
                    id = index,
                    symbolIndex = symbol,
                    state = CardState.Revealed
                )
            }

        return GameUiState(
            cards = cards,
            phase = GamePhase.Preview,
            previewSecondsLeft = ceil(difficulty.previewDurationMs / 1_000f).toInt(),
            previewFraction = 1f,
            moves = 0,
            elapsedMs = 0,
            best = best,
            result = null,
            round = round,
        )
    }

    private fun List<MemoryCard>.withState(ids: Set<Int>, state: CardState): List<MemoryCard> =
        map { card -> if (card.id in ids) card.copy(state = state) else card }

    private companion object {
        const val MISMATCH_DELAY_MS = 700L
        const val TIMER_TICK_MS = 200L
        const val PREVIEW_TICK_MS = 100L
        const val FINISH_DIALOG_DELAY_MS = 600L
    }
}
