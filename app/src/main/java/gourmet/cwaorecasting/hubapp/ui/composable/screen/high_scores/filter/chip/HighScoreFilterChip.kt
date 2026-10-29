package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter.chip

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter.HighScoreFilterItem
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

@Composable
fun HighScoreFilterChip(
    item: HighScoreFilterItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    FilterChip(
        selected = item.selected,
        onClick = onClick,
        label = { Text(item.label) },
        leadingIcon = item.icon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            }
        },
        shape = RoundedCornerShape(dimens.controlCornerRadius),
        modifier = modifier,
    )
}
