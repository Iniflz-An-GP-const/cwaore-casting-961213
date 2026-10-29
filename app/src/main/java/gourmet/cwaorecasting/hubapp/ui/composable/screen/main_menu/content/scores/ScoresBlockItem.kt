package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.clearAndSetSemantics
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockEmphasis

data class ScoresStat(
    val label: String,
    val value: String?,
)

data class ScoresBlockItem(
    val title: String,
    val icon: ImageVector,
    val moves: ScoresStat,
    val time: ScoresStat,
    /** Shown instead of the numbers while there is no record at all. */
    val emptyHint: String,
    val emphasis: MenuBlockEmphasis = MenuBlockEmphasis.Neutral,
) {
    val isEmpty: Boolean get() = moves.value == null && time.value == null
}

/** Keeps the element's space but makes it invisible and silent for accessibility. */
internal fun Modifier.hiddenWhen(hidden: Boolean): Modifier =
    if (hidden) alpha(0f).clearAndSetSemantics {} else this
