package gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.DifficultyOptionItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.difficultyOptionSurface
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun DifficultyOptionShell(
    item: DifficultyOptionItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (DifficultyOptionItem) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val containerColor = if (item.selected) colors.primaryContainer else colors.secondaryContainer
    val contentColor = if (item.selected) colors.onPrimaryContainer else colors.onSecondaryContainer

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .difficultyOptionSurface(
                    onClick = onClick,
                    shape = RoundedCornerShape(LocalDimens.current.controlCornerRadius),
                    containerColor = containerColor,
                    border = null,
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            content(item)
        }
    }
}
