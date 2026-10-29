package gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.DifficultyOptionDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option.DifficultyOptionItem
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun DifficultyOptionContent(
    item: DifficultyOptionItem,
    meter: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.tightSpacing),
    ) {
        meter()
        Text(text = item.title, style = DifficultyOptionDefaults.titleStyle)
        Text(
            text = DifficultyOptionDefaults.subtitle(item),
            color = DifficultyOptionDefaults.secondaryContentColor,
            style = DifficultyOptionDefaults.subtitleStyle,
        )
    }
}
