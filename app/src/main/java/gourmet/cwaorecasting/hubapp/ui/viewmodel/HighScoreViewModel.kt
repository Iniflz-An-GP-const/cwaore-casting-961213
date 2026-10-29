package gourmet.cwaorecasting.hubapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gourmet.cwaorecasting.hubapp.data.entity.HighScore
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.data.model.HighScoreSortType
import gourmet.cwaorecasting.hubapp.data.repository.HighScoreRepository
import gourmet.cwaorecasting.hubapp.ui.state.DataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** A game with its place among the games of the same difficulty, best first. */
data class RankedHighScore(
    val score: HighScore,
    val rank: Int,
)

class HighScoreViewModel(
    private val highScoreRepository: HighScoreRepository
) : ViewModel() {

    private val _allScores = MutableStateFlow<List<HighScore>?>(null)
    private val _selectedDifficultyState = MutableStateFlow(Difficulty.Easy)
    private val _selectedSortTypeState = MutableStateFlow(HighScoreSortType.MOVES)

    val selectedDifficultyState: StateFlow<Difficulty> = _selectedDifficultyState.asStateFlow()
    val selectedSortTypeState: StateFlow<HighScoreSortType> = _selectedSortTypeState.asStateFlow()

    val highScoresState: StateFlow<DataUiState<List<RankedHighScore>>> =
        combine(
            _allScores,
            _selectedDifficultyState,
            _selectedSortTypeState
        ) { scores, difficulty, sortType ->
            if (scores == null) {
                DataUiState.Initial
            } else {
                DataUiState.from(
                    rankAndSort(
                        scores.filter { it.difficulty == difficulty },
                        sortType
                    )
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataUiState.Initial
        )

    val hasScoresState: StateFlow<Boolean> =
        _allScores.map { !it.isNullOrEmpty() }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        loadHighScores()
    }

    fun loadHighScores() {
        viewModelScope.launch {
            _allScores.value = highScoreRepository.getAll()
        }
    }

    fun onDifficultySelected(difficulty: Difficulty) {
        _selectedDifficultyState.value = difficulty
    }

    fun onSortTypeSelected(sortType: HighScoreSortType) {
        _selectedSortTypeState.value = sortType
    }

    fun deleteHighScores() {
        viewModelScope.launch {
            highScoreRepository.deleteAll()
            _allScores.value = emptyList()
        }
    }

    private fun rankAndSort(
        scores: List<HighScore>,
        sortType: HighScoreSortType
    ): List<RankedHighScore> {
        val ranked = scores
            .sortedWith(compareBy<HighScore> { it.moves }.thenBy { it.timeMs })
            .mapIndexed { index, score -> RankedHighScore(score = score, rank = index + 1) }

        return when (sortType) {
            HighScoreSortType.MOVES -> ranked
            HighScoreSortType.TIME -> ranked.sortedWith(
                compareBy(
                    { it.score.timeMs },
                    { it.score.moves })
            )

            HighScoreSortType.DATE -> ranked.sortedByDescending { it.score.timestamp }
        }
    }
}
