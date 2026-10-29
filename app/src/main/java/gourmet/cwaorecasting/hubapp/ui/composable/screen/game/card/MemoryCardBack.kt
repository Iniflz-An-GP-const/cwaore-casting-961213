package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.back.MemoryCardBack as MemoryCardBackVariant

/**
 * The face-down side of a card. This is the single switch point: change the import to try
 * another back (v_question, v_pattern, v_gradient, v_framed).
 */
@Composable
internal fun MemoryCardBack(modifier: Modifier = Modifier) {
    MemoryCardBackVariant(modifier = modifier)
}
