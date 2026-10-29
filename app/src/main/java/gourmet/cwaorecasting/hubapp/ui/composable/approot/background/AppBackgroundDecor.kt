package gourmet.cwaorecasting.hubapp.ui.composable.approot.background

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.BackgroundPattern
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.PlacedIcon
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.placeBackgroundIcons
import kotlin.random.Random

private data class FadeTiming(
    val durationMs: Int,
    val delayMs: Int,
)

@Composable
fun AppBackgroundDecor(pattern: BackgroundPattern, modifier: Modifier = Modifier) {
    val items = remember(pattern) {
        val random = Random(System.nanoTime())
        placeBackgroundIcons(random, pattern).map { item ->
            item to FadeTiming(
                durationMs = 2500 + random.nextInt(2000),
                delayMs = random.nextInt(2000),
            )
        }
    }

    Box(modifier = modifier) {
        items.forEach { (item, timing) -> FadeDecorIcon(item, timing) }
    }
}

@Composable
private fun BoxScope.FadeDecorIcon(item: PlacedIcon, timing: FadeTiming) {
    val transition = rememberInfiniteTransition(label = "menuBackgroundFade")

    val fade by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = timing.durationMs,
                delayMillis = timing.delayMs,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "fade",
    )

    Icon(
        imageVector = item.icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .align(item.anchor)
            .size(item.size)
            .graphicsLayer { alpha = item.baseAlpha * fade },
    )
}
