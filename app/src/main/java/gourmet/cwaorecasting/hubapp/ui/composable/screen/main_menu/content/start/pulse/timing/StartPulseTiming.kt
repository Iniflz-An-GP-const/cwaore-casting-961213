package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse.timing

import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse.PulseTiming

/** Slow and gentle: long pulses, long rests, a weak amplitude. */
val StartPulseTiming = PulseTiming(
    durationMs = 2_000,
    pauseMs = 1_500,
    intensity = 0.7f,
)
