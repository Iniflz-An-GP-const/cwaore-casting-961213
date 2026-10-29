package gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.data.model.HighScoreSortType
import gourmet.cwaorecasting.hubapp.ui.composable.approot.topbar.AppTopBar
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.titleRes
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.formatElapsed
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card.HighScoreCardItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.card.HighScoreCard
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter.HighScoreFilterItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.high_scores.filter.HighScoreFilter
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.DataBasedContainer
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.DataEmptyContent
import gourmet.cwaorecasting.hubapp.ui.state.DataUiState
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.viewmodel.HighScoreViewModel
import gourmet.cwaorecasting.hubapp.ui.viewmodel.RankedHighScore
import org.koin.compose.viewmodel.koinViewModel
import java.time.format.DateTimeFormatter

private val DateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

@Composable
fun GTENPHighScoresScreen(
    modifier: Modifier = Modifier,
    viewModel: HighScoreViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val highScoresState by viewModel.highScoresState.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficultyState.collectAsState()
    val selectedSortType by viewModel.selectedSortTypeState.collectAsState()
    val dimens = LocalDimens.current

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.gtenp_high_scores_title),
            onNavigateBack = onBackClick,
        )

        HighScoreFilter(
            items = Difficulty.entries.map { difficulty ->
                HighScoreFilterItem(
                    label = stringResource(difficulty.titleRes()),
                    selected = difficulty == selectedDifficulty,
                    icon = difficulty.icon(),
                )
            },
            onSelect = { index -> viewModel.onDifficultySelected(Difficulty.entries[index]) },
            modifier = Modifier.padding(
                horizontal = dimens.screenPadding,
                vertical = dimens.contentSpacing,
            ),
        )

        DataBasedContainer(
            modifier = Modifier.weight(1f),
            dataState = highScoresState,
            dataPopulated = {
                val highScores = (highScoresState as DataUiState.Populated).data

                Column(modifier = Modifier.fillMaxSize()) {
                    HighScoreFilter(
                        items = HighScoreSortType.entries.map { sortType ->
                            HighScoreFilterItem(
                                label = stringResource(sortType.labelRes()),
                                selected = sortType == selectedSortType,
                                icon = sortType.icon(),
                            )
                        },
                        onSelect = { index -> viewModel.onSortTypeSelected(HighScoreSortType.entries[index]) },
                        modifier = Modifier.padding(
                            start = dimens.screenPadding,
                            end = dimens.screenPadding,
                            bottom = dimens.contentSpacing,
                        ),
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            start = dimens.screenPadding,
                            end = dimens.screenPadding,
                            bottom = dimens.contentSpacing,
                        ),
                        verticalArrangement = Arrangement.spacedBy(dimens.contentSpacing),
                    ) {
                        items(highScores, key = { it.score.id }) { ranked ->
                            HighScoreCard(item = ranked.toCardItem())
                        }
                    }
                }
            },
            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.high_scores_empty),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

private fun RankedHighScore.toCardItem() = HighScoreCardItem(
    rank = rank,
    moves = score.moves,
    formattedTime = formatElapsed(score.timeMs),
    formattedDate = score.timestamp.format(DateFormatter),
)

private fun HighScoreSortType.labelRes(): Int = when (this) {
    HighScoreSortType.MOVES -> R.string.high_scores_filter_moves
    HighScoreSortType.TIME -> R.string.high_scores_filter_time
    HighScoreSortType.DATE -> R.string.high_scores_filter_date
}

private fun HighScoreSortType.icon() = when (this) {
    HighScoreSortType.MOVES -> Icons.Filled.Numbers
    HighScoreSortType.TIME -> Icons.Filled.Timer
    HighScoreSortType.DATE -> Icons.Filled.DateRange
}

private fun Difficulty.icon() = when (this) {
    Difficulty.Easy -> Icons.Filled.StarBorder
    Difficulty.Medium -> Icons.AutoMirrored.Filled.StarHalf
    Difficulty.Hard -> Icons.Filled.Star
}
