package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.mismatch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.CardFeedbackDefaults
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private val ShakeReach = 8.dp

/** Two unmatched cards shake from side to side, the swing dying down, as if saying "no". */
@Composable
fun Modifier.mismatchEffect(isMismatched: Boolean): Modifier {
    val swing = remember { Animatable(0f) }
    val reachPx = with(LocalDensity.current) { ShakeReach.toPx() }

    LaunchedEffect(isMismatched) {
        if (isMismatched) {
            delay(CardFeedbackDefaults.MismatchDelayMs.milliseconds)
            // Unit swings, scaled to the reach by the layer below
            swing.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = CardFeedbackDefaults.MismatchDurationMs
                    -1f at 50
                    1f at 130
                    -0.8f at 210
                    0.8f at 290
                    -0.4f at 350
                },
            )
        } else {
            swing.snapTo(0f)
        }
    }

    return graphicsLayer { translationX = swing.value * reachPx }
}
