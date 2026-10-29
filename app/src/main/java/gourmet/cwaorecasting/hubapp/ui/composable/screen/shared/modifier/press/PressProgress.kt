package gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.modifier.press

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.collectLatest

private const val PRESS_DURATION_MS = 100

/**
 * Press progress shared by every press-effect variant: 0 = at rest, 1 = fully pressed.
 *
 * A quick tap still plays the full press before springing back, so the feedback is visible
 * even when the press is shorter than the animation. The release spring overshoots slightly
 * below 0, which gives each variant its small bounce.
 */
@Composable
internal fun rememberPressProgress(interactionSource: InteractionSource): Animatable<Float, AnimationVector1D> {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> progress.animateTo(1f, tween(PRESS_DURATION_MS))
                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    progress.animateTo(1f, tween(PRESS_DURATION_MS))
                    progress.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium,
                        ),
                    )
                }
            }
        }
    }

    return progress
}
