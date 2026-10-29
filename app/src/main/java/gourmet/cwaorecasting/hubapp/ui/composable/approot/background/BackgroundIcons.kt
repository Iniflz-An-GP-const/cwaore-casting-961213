package gourmet.cwaorecasting.hubapp.ui.composable.approot.background

import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.CardSymbols
import kotlin.random.Random

/** The background wears the same symbols as the memory cards. */
val BackgroundIconPool: List<ImageVector> = CardSymbols

data class PlacedIcon(
    val icon: ImageVector,
    val anchor: BiasAlignment,
    val size: Dp,
    val baseAlpha: Float,
)

/**
 * Gives every anchor of [pattern] an icon from [BackgroundIconPool]. The pool is shuffled and
 * repeated, so a dense pattern with more anchors than icons reuses them.
 */
fun placeBackgroundIcons(random: Random, pattern: BackgroundPattern): List<PlacedIcon> {
    val anchors = pattern.anchors
    val icons = buildList {
        while (size < anchors.size) {
            addAll(BackgroundIconPool.shuffled(random))
        }
    }.take(anchors.size)

    return anchors.mapIndexed { index, anchor ->
        PlacedIcon(
            icon = icons[index],
            anchor = anchor,
            size = pattern.iconSize,
            baseAlpha = 0.09f + random.nextFloat() * 0.08f,
        )
    }
}
