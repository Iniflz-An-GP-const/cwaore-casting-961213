package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.deal

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.CardFeedbackDefaults
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val START_SCALE = 0.6f
private const val DEAL_MS = 320

@Composable
fun Modifier.dealEffect(index: Int, round: Int): Modifier {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(round) {
        progress.snapTo(0f)
        delay((index * CardFeedbackDefaults.DealStaggerMs).coerceAtMost(CardFeedbackDefaults.DealMaxDelayMs).milliseconds)
        progress.animateTo(1f, tween(durationMillis = DEAL_MS, easing = FastOutSlowInEasing))
    }

    return graphicsLayer {
        val value = progress.value
        val scale = START_SCALE + (1f - START_SCALE) * value
        scaleX = scale
        scaleY = scale
        alpha = value
    }
}
