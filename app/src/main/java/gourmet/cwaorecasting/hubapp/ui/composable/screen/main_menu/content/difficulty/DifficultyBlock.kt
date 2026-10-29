package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.difficulty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.DifficultyOption
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.DifficultyOptionItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.difficulty.DifficultyBlockItem
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun DifficultyBlock(
    item: DifficultyBlockItem,
    onSelect: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        Text(
            text = item.title,
            color = MenuBlockDefaults.contentColor,
            style = MaterialTheme.typography.titleMedium,
        )

        item.levels.forEachIndexed { index, level ->
            DifficultyOption(
                item = DifficultyOptionItem(
                    title = level.title,
                    cardCount = level.rows * level.columns,
                    pairCount = level.rows * level.columns / 2,
                    rows = level.rows,
                    columns = level.columns,
                    level = index + 1,
                    levelCount = item.levels.size,
                    selected = index == item.selectedIndex,
                ),
                onClick = { onSelect(index) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
