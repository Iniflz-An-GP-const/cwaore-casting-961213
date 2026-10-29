package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockItem
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun MenuBlockContent(
    item: MenuBlockItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(
            LocalDimens.current.tightSpacing,
            alignment = Alignment.CenterVertically
        ),
    ) {
        Text(text = item.title, style = MaterialTheme.typography.titleMedium)
        item.subtitle?.let { subtitle ->
            Text(
                text = subtitle,
                color = LocalContentColor.current.copy(alpha = MenuBlockDefaults.SecondaryAlpha),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
