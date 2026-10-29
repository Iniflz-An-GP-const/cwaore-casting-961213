package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.modifier.press.pressFeedback
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.theme.cardBorderStroke

internal object MenuBlockDefaults {
    val MinHeight: Dp = 104.dp
    val IconSize: Dp = 28.dp

    const val SecondaryAlpha: Float = 0.7f

    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceContainerLow

    val contentColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val secondaryContentColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun emphasisContainerColor(emphasis: MenuBlockEmphasis): Color =
        with(MaterialTheme.colorScheme) {
            when (emphasis) {
                MenuBlockEmphasis.Neutral -> surfaceContainerLow
                MenuBlockEmphasis.Primary -> primaryContainer
                MenuBlockEmphasis.Secondary -> secondaryContainer
                MenuBlockEmphasis.Tertiary -> tertiaryContainer
                MenuBlockEmphasis.Muted -> surfaceVariant
            }
        }

    @Composable
    fun emphasisContentColor(emphasis: MenuBlockEmphasis): Color = with(MaterialTheme.colorScheme) {
        when (emphasis) {
            MenuBlockEmphasis.Neutral -> onSurface
            MenuBlockEmphasis.Primary -> onPrimaryContainer
            MenuBlockEmphasis.Secondary -> onSecondaryContainer
            MenuBlockEmphasis.Tertiary -> onTertiaryContainer
            MenuBlockEmphasis.Muted -> onSurfaceVariant
        }
    }

    @Composable
    fun emphasisAccentColor(emphasis: MenuBlockEmphasis): Color = with(MaterialTheme.colorScheme) {
        when (emphasis) {
            MenuBlockEmphasis.Neutral -> outline
            MenuBlockEmphasis.Primary -> primary
            MenuBlockEmphasis.Secondary -> secondary
            MenuBlockEmphasis.Tertiary -> tertiary
            MenuBlockEmphasis.Muted -> outlineVariant
        }
    }
}

internal val LocalMenuBlockBare = staticCompositionLocalOf { false }

@Composable
internal fun Modifier.menuBlockSurface(
    onClick: (() -> Unit)? = null,
    containerColor: Color? = null,
    border: BorderStroke? = null,
): Modifier {
    val shape = MenuBlockDefaults.shape
    val isBare = LocalMenuBlockBare.current
    val borderStroke = if (isBare) null else border ?: cardBorderStroke()
    val fill = containerColor ?: if (isBare) null else MenuBlockDefaults.containerColor
    val hasPadding = !isBare || containerColor != null
    val interactionSource = remember { MutableInteractionSource() }

    return this
        .let { base -> if (onClick != null) base.pressFeedback(interactionSource) else base }
        .clip(shape)
        .let { base -> if (fill != null) base.background(color = fill, shape = shape) else base }
        .let { base -> if (borderStroke != null) base.border(borderStroke, shape) else base }
        .let { base ->
            if (onClick != null) {
                base.clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onClick = onClick,
                )
            } else {
                base
            }
        }
        .let { base -> if (hasPadding) base.padding(LocalDimens.current.cardPadding) else base }
}

@Composable
internal fun Modifier.menuBlockMinHeight(): Modifier = heightIn(min = MenuBlockDefaults.MinHeight)
