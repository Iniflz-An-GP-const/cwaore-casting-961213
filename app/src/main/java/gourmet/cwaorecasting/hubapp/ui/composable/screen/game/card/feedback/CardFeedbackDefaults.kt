package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback

internal object CardFeedbackDefaults {
    /** The success effect waits for the second card to finish turning over. */
    const val MatchDelayMs = 350L

    /** The mismatch effect starts once the face is mostly turned up and ends before the cards
     * close. */
    const val MismatchDelayMs = 250L
    const val MismatchDurationMs = 400

    /** Each card of the deal starts this much later than the previous one. */
    const val DealStaggerMs = 35L
    const val DealMaxDelayMs = 700L
}
