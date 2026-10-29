package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.field.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun gameFieldShellPadding(): Dp = LocalDimens.current.cardPadding

@Composable
fun GameFieldShell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .background(color = MaterialTheme.colorScheme.secondaryContainer, shape = shape)
            .padding(gameFieldShellPadding()),
    ) {
        content()
    }
}
