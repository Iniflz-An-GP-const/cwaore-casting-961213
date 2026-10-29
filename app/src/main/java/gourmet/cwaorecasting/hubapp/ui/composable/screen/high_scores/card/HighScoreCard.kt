package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card.HighScoreCardDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card.HighScoreCardItem
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.theme.cardBorderStroke

private val DividerHeight = 32.dp

@Composable
fun HighScoreCard(
    item: HighScoreCardItem,
    modifier: Modifier = Modifier,
) {
    val shape = HighScoreCardDefaults.shape
    val border = cardBorderStroke()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = shape)
            .let { base -> if (border != null) base.border(border, shape) else base }
            .padding(HighScoreCardDefaults.padding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StatColumn(
            value = item.moves.toString(),
            label = stringResource(R.string.high_score_card_moves_label),
            modifier = Modifier.weight(0.85f),
        )
        VerticalDivider(
            modifier = Modifier.height(DividerHeight),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        StatColumn(
            value = item.formattedTime,
            label = stringResource(R.string.high_score_card_time_label),
            modifier = Modifier.weight(0.85f),
        )
        VerticalDivider(
            modifier = Modifier.height(DividerHeight),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        StatColumn(
            value = item.formattedDate,
            label = stringResource(R.string.high_score_card_date_label),
            modifier = Modifier.weight(1.3f),
        )
    }
}

@Composable
private fun StatColumn(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.tightSpacing),
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
