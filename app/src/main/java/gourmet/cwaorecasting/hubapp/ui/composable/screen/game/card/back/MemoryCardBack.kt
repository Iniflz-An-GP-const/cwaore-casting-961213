package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.back

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCardDefaults
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

private val FrameInset = 6.dp
private val FrameWidth = 2.dp
private const val FrameAlpha = 0.45f
private const val EmblemFraction = 0.4f

@Composable
fun MemoryCardBack(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val frameColor = colors.onPrimaryContainer.copy(alpha = FrameAlpha)
    val frameShape = RoundedCornerShape(LocalDimens.current.cornerRadius * 0.6f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.primaryContainer, shape = MemoryCardDefaults.shape)
            .padding(FrameInset)
            .border(width = FrameWidth, color = frameColor, shape = frameShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Diamond,
            contentDescription = null,
            tint = colors.onPrimaryContainer,
            modifier = Modifier.fillMaxSize(EmblemFraction),
        )
    }
}
