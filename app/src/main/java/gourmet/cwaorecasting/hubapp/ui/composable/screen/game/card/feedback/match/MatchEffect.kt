package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.match

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.CardFeedbackDefaults
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val RING_MS = 650
private const val RING_START_ALPHA = 0.6f
private val RingReach = 10.dp
private val RingWidth = 3.dp

@Composable
fun Modifier.matchEffect(isMatched: Boolean): Modifier {
    val progress = remember { Animatable(1f) }
    val color = MaterialTheme.colorScheme.tertiary
    val corner = LocalDimens.current.cornerRadius

    LaunchedEffect(isMatched) {
        if (isMatched) {
            delay(CardFeedbackDefaults.MatchDelayMs.milliseconds)
            progress.snapTo(0f)
            progress.animateTo(1f, tween(durationMillis = RING_MS, easing = LinearOutSlowInEasing))
        } else {
            progress.snapTo(1f)
        }
    }

    return drawBehind {
        val value = progress.value
        if (value >= 1f) return@drawBehind

        val reach = RingReach.toPx() * value
        val radius = corner.toPx() + reach

        drawRoundRect(
            color = color.copy(alpha = RING_START_ALPHA * (1f - value)),
            topLeft = Offset(-reach, -reach),
            size = Size(size.width + 2 * reach, size.height + 2 * reach),
            cornerRadius = CornerRadius(radius, radius),
            style = Stroke(width = RingWidth.toPx()),
        )
    }
}
