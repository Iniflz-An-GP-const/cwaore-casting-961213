package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse.timing.StartPulseTiming
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse.pulseEffect

/**
 * Attention pulse of the start control. This is the single switch point for both axes:
 * change the `pulseEffect` import to try another effect (v_breath, v_halo, v_nudge, v_shimmer)
 * and the `StartPulseTiming` import to try another tempo (v_calm, v_steady, v_lively, v_off).
 *
 * [cornerRadius] is the shape of the pulsing element, for effects that draw around it or clip to it.
 * Place it first in the modifier chain so the whole element is affected.
 */
@Composable
fun Modifier.startPulse(cornerRadius: Dp): Modifier = pulseEffect(cornerRadius, StartPulseTiming)
