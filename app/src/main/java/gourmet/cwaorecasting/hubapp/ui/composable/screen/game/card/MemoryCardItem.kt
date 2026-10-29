package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card

import androidx.compose.ui.graphics.vector.ImageVector
import gourmet.cwaorecasting.hubapp.data.model.CardState

data class MemoryCardItem(
    val symbol: ImageVector,
    val state: CardState,
)
