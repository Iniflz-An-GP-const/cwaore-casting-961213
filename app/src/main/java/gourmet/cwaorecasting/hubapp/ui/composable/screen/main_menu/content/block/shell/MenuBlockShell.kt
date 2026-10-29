package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockEmphasis
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.menuBlockMinHeight
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.menuBlockSurface

@Composable
fun MenuBlockShell(
    onClick: () -> Unit,
    emphasis: MenuBlockEmphasis,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val isNeutral = emphasis == MenuBlockEmphasis.Neutral
    val containerColor =
        if (isNeutral) colors.secondaryContainer else MenuBlockDefaults.emphasisContainerColor(
            emphasis
        )
    val contentColor =
        if (isNeutral) colors.onSecondaryContainer else MenuBlockDefaults.emphasisContentColor(
            emphasis
        )

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(
            modifier = modifier
                .menuBlockMinHeight()
                .menuBlockSurface(onClick = onClick, containerColor = containerColor),
            propagateMinConstraints = true,
        ) {
            content()
        }
    }
}
