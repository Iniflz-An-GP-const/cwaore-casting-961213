package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import gourmet.cwaorecasting.hubapp.data.model.CardState
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCardBack
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCardDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCardFace
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCardItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.modifier.press.pressFeedback

private const val FADE_DURATION_MS = 250

@Composable
fun MemoryCard(
    item: MemoryCardItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Crossfade(
        targetState = item.state != CardState.Hidden,
        modifier = modifier
            .pressFeedback(interactionSource)
            .clip(MemoryCardDefaults.shape)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = item.state == CardState.Hidden,
                onClick = onClick,
            ),
        animationSpec = tween(durationMillis = FADE_DURATION_MS),
        label = "cardFade",
    ) { isFaceUp ->
        if (isFaceUp) {
            MemoryCardFace(symbol = item.symbol, state = item.state)
        } else {
            MemoryCardBack()
        }
    }
}
