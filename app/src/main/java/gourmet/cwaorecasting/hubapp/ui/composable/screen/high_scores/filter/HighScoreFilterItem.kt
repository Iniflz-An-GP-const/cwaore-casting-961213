package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter

import androidx.compose.ui.graphics.vector.ImageVector

data class HighScoreFilterItem(
    val label: String,
    val selected: Boolean,
    val icon: ImageVector? = null,
)
