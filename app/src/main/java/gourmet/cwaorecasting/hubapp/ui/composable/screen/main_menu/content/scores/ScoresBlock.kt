package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.shell.MenuBlockShell
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.ScoresBlockContent

@Composable
fun ScoresBlock(
    item: ScoresBlockItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MenuBlockShell(onClick = onClick, emphasis = item.emphasis, modifier = modifier) {
        ScoresBlockContent(item = item)
    }
}
