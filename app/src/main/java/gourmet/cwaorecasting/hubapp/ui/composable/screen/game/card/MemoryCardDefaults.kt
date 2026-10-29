package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import gourmet.cwaorecasting.hubapp.data.model.CardState
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.theme.cardBorderStroke

internal object MemoryCardDefaults {
    const val SYMBOL_SIZE_FRACTION = 0.5f

    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cornerRadius)

    @Composable
    fun faceContainerColor(state: CardState): Color = when (state) {
        CardState.Matched -> MaterialTheme.colorScheme.tertiaryContainer
        CardState.Mismatch -> MaterialTheme.colorScheme.errorContainer
        // The lightest tier, so a face-up card stands out from the field panel (surfaceContainerLow)
        else -> MaterialTheme.colorScheme.surfaceContainerLowest
    }

    @Composable
    fun faceContentColor(state: CardState): Color = when (state) {
        CardState.Matched -> MaterialTheme.colorScheme.onTertiaryContainer
        CardState.Mismatch -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.primary
    }
}

@Composable
internal fun MemoryCardFace(
    symbol: ImageVector,
    state: CardState,
    modifier: Modifier = Modifier,
) {
    val shape = MemoryCardDefaults.shape
    val border = cardBorderStroke()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MemoryCardDefaults.faceContainerColor(state), shape = shape)
            .let { base -> if (border != null) base.border(border, shape) else base },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = symbol,
            contentDescription = null,
            tint = MemoryCardDefaults.faceContentColor(state),
            modifier = Modifier.fillMaxSize(MemoryCardDefaults.SYMBOL_SIZE_FRACTION),
        )
    }
}
