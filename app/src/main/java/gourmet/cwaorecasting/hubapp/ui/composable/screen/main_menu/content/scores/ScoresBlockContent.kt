package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.ScoresBlockItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.ScoresStat
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.hiddenWhen
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

private const val PlaceholderValue = "–"

@Composable
fun ScoresBlockContent(
    item: ScoresBlockItem,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(MenuBlockDefaults.IconSize),
            )
            Text(text = item.title, style = MaterialTheme.typography.titleMedium)
        }

        // The lines are always laid out, so the block keeps one height with or without
        // records; while empty they are invisible and the hint sits over them
        Box(contentAlignment = Alignment.CenterStart) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .hiddenWhen(item.isEmpty),
                verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
            ) {
                StatLine(stat = item.moves)
                StatLine(stat = item.time)
            }

            if (item.isEmpty) {
                Text(
                    text = item.emptyHint,
                    color = LocalContentColor.current.copy(alpha = MenuBlockDefaults.SecondaryAlpha),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun StatLine(stat: ScoresStat) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = stat.label,
            color = LocalContentColor.current.copy(alpha = MenuBlockDefaults.SecondaryAlpha),
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = stat.value ?: PlaceholderValue,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
