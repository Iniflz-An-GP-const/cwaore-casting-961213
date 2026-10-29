package gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.modifier.press.pressFeedback
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.theme.cardBorderStroke

internal object DifficultyOptionDefaults {
    val MinHeight: Dp = 72.dp
    private val SelectedBorderWidth: Dp = 2.dp

    /** Alpha of the unfilled part of a level meter, and of secondary text, over the content color. */
    const val INACTIVE_ALPHA: Float = 0.25f
    const val SECONDARY_ALPHA: Float = 0.7f

    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceContainerLow

    val titleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.titleMedium

    val subtitleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium

    val secondaryContentColor: Color
        @Composable get() = LocalContentColor.current.copy(alpha = SECONDARY_ALPHA)

    @Composable
    fun border(selected: Boolean): BorderStroke? =
        if (selected) BorderStroke(
            SelectedBorderWidth,
            MaterialTheme.colorScheme.primary
        ) else cardBorderStroke()

    @Composable
    fun subtitle(item: DifficultyOptionItem): String =
        stringResource(R.string.difficulty_cards_pairs_format, item.cardCount, item.pairCount)
}

@Composable
internal fun Modifier.difficultyOptionSurface(
    onClick: () -> Unit,
    shape: Shape = DifficultyOptionDefaults.shape,
    containerColor: Color = DifficultyOptionDefaults.containerColor,
    border: BorderStroke? = cardBorderStroke(),
    padded: Boolean = true,
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }

    return this
        .pressFeedback(interactionSource)
        .heightIn(min = DifficultyOptionDefaults.MinHeight)
        .clip(shape)
        .background(color = containerColor, shape = shape)
        .let { base -> if (border != null) base.border(border, shape) else base }
        .clickable(
            interactionSource = interactionSource,
            indication = LocalIndication.current,
            onClick = onClick,
        )
        .let { base -> if (padded) base.padding(LocalDimens.current.cardPadding) else base }
}
