package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.content.GameHudContent
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.shell.GameHudShell

@Composable
fun GameHud(
    item: GameHudItem,
    modifier: Modifier = Modifier,
) {
    GameHudShell(modifier = modifier) {
        GameHudContent(item = item)
    }
}
