package gourmet.cwaorecasting.hubapp.data.model

const val CARD_SYMBOL_COUNT = 14

enum class CardState {
    Hidden,

    Revealed,

    Matched,

    Mismatch,
}

enum class GamePhase {
    Preview,
    Playing,
    Finished,
}

data class MemoryCard(
    val id: Int,
    val symbolIndex: Int,
    val state: CardState,
)

data class GameUiState(
    val cards: List<MemoryCard>,
    val phase: GamePhase,
    val previewSecondsLeft: Int,
    val previewFraction: Float,
    val moves: Int,
    val elapsedMs: Long,
    val best: BestScores,
    val result: GameResult?,
    /** Counts from 0; grows with every new deal, so animations of the deal can run again. */
    val round: Int,
)
