package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse.PulseTiming

private const val HALO_START_ALPHA = 0.5f
private val HaloReach = 10.dp
private val HaloWidth = 3.dp

/**
 * An outline grows out of the element and fades away: one ring takes `durationMs`, then the
 * element rests for `pauseMs`. `intensity` scales how far the ring reaches and how bright it is.
 * It is drawn around the element, so it needs about [HaloReach] of free space around it.
 */
@Composable
fun Modifier.pulseEffect(cornerRadius: Dp, timing: PulseTiming): Modifier {
    if (timing.isOff) return this

    val color = MaterialTheme.colorScheme.primary
    val progress = rememberInfiniteTransition(label = "startHalo").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = timing.cycleMs
                0f at 0
                1f at timing.durationMs using LinearOutSlowInEasing
                if (timing.pauseMs > 0) 1f at timing.cycleMs
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "startHaloProgress",
    )

    return drawBehind {
        val value = progress.value
        val reach = HaloReach.toPx() * timing.intensity * value
        val radius = cornerRadius.toPx() + reach
        val alpha = HALO_START_ALPHA * timing.intensity.coerceAtMost(1f) * (1f - value)

        drawRoundRect(
            color = color.copy(alpha = alpha),
            topLeft = Offset(-reach, -reach),
            size = Size(size.width + 2 * reach, size.height + 2 * reach),
            cornerRadius = CornerRadius(radius, radius),
            style = Stroke(width = HaloWidth.toPx()),
        )
    }
}
