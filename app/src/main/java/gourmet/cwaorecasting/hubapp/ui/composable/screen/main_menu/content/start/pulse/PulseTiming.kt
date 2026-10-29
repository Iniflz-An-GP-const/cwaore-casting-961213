package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.pulse

/**
 * How a pulse effect is paced. Every effect reads the same three fields, each in its own way,
 * so any tempo works with any effect. A tempo variant declares one value of this type.
 */
data class PulseTiming(
    /** How long one pulse takes: a breath, a growing ring, a burst of bumps, a sweep of light. */
    val durationMs: Int,
    /** Rest between two pulses. */
    val pauseMs: Int,
    /** Strength of the pulse: 1 is the standard amplitude, 0 turns the effect off. */
    val intensity: Float,
) {
    val isOff: Boolean get() = intensity <= 0f

    /** One full cycle: the pulse and the rest after it. */
    val cycleMs: Int get() = durationMs + pauseMs
}
