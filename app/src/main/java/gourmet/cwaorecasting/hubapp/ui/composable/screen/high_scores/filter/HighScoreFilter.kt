package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter.HighScoreFilterItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter.chip.HighScoreFilterChip
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.theme.cardBorderStroke

@Composable
fun HighScoreFilter(
    items: List<HighScoreFilterItem>,
    onSelect: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = cardBorderStroke(),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimens.tightSpacing),
            contentPadding = PaddingValues(horizontal = dimens.cardPadding),
            horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        ) {
            itemsIndexed(items) { index, item ->
                HighScoreFilterChip(item = item, onClick = { onSelect(index) })
            }
        }
    }
}
