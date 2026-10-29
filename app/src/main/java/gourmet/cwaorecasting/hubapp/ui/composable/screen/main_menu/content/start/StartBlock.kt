package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.menuBlockSurface
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.StartBlockItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.startPulse
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.button.ActionButtonItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.button.ActionButton
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun StartBlock(
    item: StartBlockItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier.menuBlockSurface(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.tightSpacing),
    ) {
        ActionButton(
            item = ActionButtonItem(text = item.text, icon = Icons.Filled.PlayArrow),
            onClick = onClick,
            modifier = Modifier.startPulse(cornerRadius = dimens.controlCornerRadius),
        )
        item.subtitle?.let { subtitle ->
            Text(
                text = subtitle,
                color = MenuBlockDefaults.secondaryContentColor,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
