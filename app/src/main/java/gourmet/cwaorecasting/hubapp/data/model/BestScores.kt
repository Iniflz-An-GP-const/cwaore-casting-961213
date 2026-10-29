package gourmet.cwaorecasting.hubapp.data.model

data class BestScores(
    val bestMoves: Int?,
    val bestTimeMs: Long?,
)

data class GameResult(
    val moves: Int,
    val timeMs: Long,
    val isNewBestMoves: Boolean,
    val isNewBestTime: Boolean,
    val best: BestScores,
)
