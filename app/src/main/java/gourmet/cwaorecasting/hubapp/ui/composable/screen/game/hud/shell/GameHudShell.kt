package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.shell

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.gameHudSurface

@Composable
fun GameHudShell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    CompositionLocalProvider(LocalContentColor provides colors.onSecondaryContainer) {
        Box(modifier = modifier.gameHudSurface(containerColor = colors.secondaryContainer)) {
            content()
        }
    }
}
