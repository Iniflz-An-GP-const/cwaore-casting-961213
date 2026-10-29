package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.data.model.CardState
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.deal.dealEffect
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.match.matchEffect
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.feedback.mismatch.mismatchEffect

/**
 * What a card does besides turning over: it appears at the deal, it celebrates a found pair and
 * it shakes off a mistake. This is the single switch point of three axes: change the
 * `dealEffect` (v_cascade, v_drop), `matchEffect` (v_bounce, v_ring, v_glow) and
 * `mismatchEffect` (v_shake, v_wobble) imports to try another combination.
 *
 * [index] is the card's place in the deal and [round] changes with every new deal, so the
 * appearing runs again after "Play again".
 */
@Composable
fun Modifier.cardFeedback(state: CardState, index: Int, round: Int): Modifier = this
    .dealEffect(index = index, round = round)
    .matchEffect(isMatched = state == CardState.Matched)
    .mismatchEffect(isMismatched = state == CardState.Mismatch)
