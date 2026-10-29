package gourmet.cwaorecasting.hubapp.ui.composable.screen.game

fun formatElapsed(ms: Long): String {
    val totalSeconds = ms / 1_000
    return "%d:%02d".format(totalSeconds / 60, totalSeconds % 60)
}
