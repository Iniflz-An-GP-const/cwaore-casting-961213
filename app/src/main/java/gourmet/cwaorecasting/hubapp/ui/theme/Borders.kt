package gourmet.cwaorecasting.hubapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun cardBorderStroke(
    color: Color = MaterialTheme.colorScheme.outlineVariant,
): BorderStroke? {
    val width = LocalDimens.current.cardBorderWidth
    return if (width > 0.dp) {
        BorderStroke(width, color)
    } else {
        null
    }
}

@Composable
fun controlBorderStroke(
    color: Color = MaterialTheme.colorScheme.outlineVariant,
): BorderStroke? {
    val width = LocalDimens.current.controlBorderWidth
    return if (width > 0.dp) BorderStroke(width, color) else null
}

@Composable
fun Modifier.imageBorder(shape: Shape): Modifier {
    val width = LocalDimens.current.imageBorderWidth
    return if (width > 0.dp) {
        border(width, MaterialTheme.colorScheme.outlineVariant, shape)
    } else {
        this
    }
}

@Composable
fun ControlDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
) {
    val width = LocalDimens.current.controlBorderWidth
    if (width > 0.dp) {
        HorizontalDivider(
            modifier = modifier,
            thickness = width,
            color = color,
        )
    }
}