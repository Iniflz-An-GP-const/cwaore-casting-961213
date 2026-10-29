package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

internal object HighScoreCardDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    val padding: Dp
        @Composable get() = LocalDimens.current.cardPadding
}
