package gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.modifier.press

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.modifier.press.pressEffect

/**
 * Press animation of every button and card. This is the single switch point: change the
 * `pressEffect` import to try another variant (v_scale, v_sink, v_squish).
 *
 * Place it before `clip`/`background` so the whole element moves, and pass the same
 * [interactionSource] to `clickable`.
 */
@Composable
fun Modifier.pressFeedback(interactionSource: InteractionSource): Modifier =
    pressEffect(interactionSource)
