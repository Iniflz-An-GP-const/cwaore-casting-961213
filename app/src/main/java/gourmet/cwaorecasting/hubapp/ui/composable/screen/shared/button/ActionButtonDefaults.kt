package gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

internal object ActionButtonDefaults {
    val Height: Dp = 56.dp
    val IconSize: Dp = 24.dp
    const val DisabledAlpha: Float = 0.38f

    val horizontalPadding: Dp
        @Composable get() = LocalDimens.current.cardPadding

    val iconTextSpacing: Dp
        @Composable get() = LocalDimens.current.itemSpacing

    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.controlCornerRadius)

    @Composable
    fun containerColor(emphasis: ActionButtonEmphasis = ActionButtonEmphasis.Default): Color =
        when (emphasis) {
            ActionButtonEmphasis.Default -> MaterialTheme.colorScheme.secondaryContainer
            ActionButtonEmphasis.Alternate -> MaterialTheme.colorScheme.tertiaryContainer
        }

    @Composable
    fun contentColor(emphasis: ActionButtonEmphasis = ActionButtonEmphasis.Default): Color =
        when (emphasis) {
            ActionButtonEmphasis.Default -> MaterialTheme.colorScheme.onSecondaryContainer
            ActionButtonEmphasis.Alternate -> MaterialTheme.colorScheme.onTertiaryContainer
        }

    @Composable
    fun labelStyle(): TextStyle = MaterialTheme.typography.titleMedium
}
