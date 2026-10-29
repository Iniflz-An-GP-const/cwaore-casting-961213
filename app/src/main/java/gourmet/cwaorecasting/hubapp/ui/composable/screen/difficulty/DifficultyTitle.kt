package gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty

import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.data.model.Difficulty

fun Difficulty.titleRes(): Int = when (this) {
    Difficulty.Easy -> R.string.difficulty_easy
    Difficulty.Medium -> R.string.difficulty_medium
    Difficulty.Hard -> R.string.difficulty_hard
}
