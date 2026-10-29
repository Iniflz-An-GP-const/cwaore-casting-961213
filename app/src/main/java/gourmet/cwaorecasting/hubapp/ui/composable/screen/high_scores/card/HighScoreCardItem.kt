package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card

data class HighScoreCardItem(
    /** 1-based place among the games of the same difficulty, best first. */
    val rank: Int,
    val moves: Int,
    val formattedTime: String,
    val formattedDate: String,
)
