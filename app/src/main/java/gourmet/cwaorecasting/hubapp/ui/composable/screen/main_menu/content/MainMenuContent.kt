package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.data.model.BestScores
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.titleRes
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.formatElapsed
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlock
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockEmphasis
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.block.MenuBlockItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.difficulty.DifficultyBlockItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.difficulty.DifficultyBlockLevel
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.difficulty.DifficultyBlock
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.header.MenuHeader
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.layout.MenuLayout
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.ScoresBlock
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.ScoresBlockItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.scores.ScoresStat
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.StartBlockItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.start.StartBlock

@Composable
fun MainMenuContent(
    selectedDifficulty: Difficulty,
    bestScores: BestScores?,
    onDifficultySelected: (index: Int) -> Unit,
    onPlayClick: () -> Unit,
    onHighScoresScreenNavigation: () -> Unit,
    onAboutScreenNavigation: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val difficultyItem = DifficultyBlockItem(
        title = stringResource(R.string.gtenp_difficulty_title),
        levels = Difficulty.entries.map { difficulty ->
            DifficultyBlockLevel(
                title = stringResource(difficulty.titleRes()),
                subtitle = stringResource(
                    R.string.difficulty_cards_pairs_format,
                    difficulty.cardCount,
                    difficulty.pairCount,
                ),
                rows = difficulty.rows,
                columns = difficulty.columns,
            )
        },
        selectedIndex = selectedDifficulty.ordinal,
        maxRows = Difficulty.entries.maxOf { it.rows },
    )

    val startItem = StartBlockItem(
        text = stringResource(R.string.main_menu_play),
        subtitle = stringResource(
            R.string.menu_start_summary_format,
            stringResource(selectedDifficulty.titleRes()),
            selectedDifficulty.cardCount,
        ),
    )

    val scoresItem = ScoresBlockItem(
        title = stringResource(R.string.main_menu_high_scores),
        icon = Icons.Filled.EmojiEvents,
        moves = ScoresStat(
            label = stringResource(R.string.menu_scores_moves_label),
            value = bestScores?.bestMoves?.toString(),
        ),
        time = ScoresStat(
            label = stringResource(R.string.menu_scores_time_label),
            value = bestScores?.bestTimeMs?.let(::formatElapsed),
        ),
        emptyHint = stringResource(R.string.menu_scores_empty),
        emphasis = MenuBlockEmphasis.Tertiary,
    )

    MenuLayout(
        modifier = modifier,
        header = { MenuHeader() },
        difficulty = { blockModifier ->
            DifficultyBlock(
                item = difficultyItem,
                onSelect = onDifficultySelected,
                modifier = blockModifier,
            )
        },
        start = { blockModifier ->
            StartBlock(
                item = startItem,
                onClick = onPlayClick,
                modifier = blockModifier,
            )
        },
        highScores = { blockModifier ->
            ScoresBlock(
                item = scoresItem,
                onClick = onHighScoresScreenNavigation,
                modifier = blockModifier,
            )
        },
        about = { blockModifier ->
            MenuBlock(
                item = MenuBlockItem(
                    title = stringResource(R.string.main_menu_about),
                    icon = Icons.Filled.Info,
                    subtitle = stringResource(R.string.menu_about_subtitle),
                ),
                onClick = onAboutScreenNavigation,
                modifier = blockModifier,
            )
        },
        exit = { blockModifier ->
            MenuBlock(
                item = MenuBlockItem(
                    title = stringResource(R.string.main_menu_exit),
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    subtitle = stringResource(R.string.menu_exit_subtitle),
                    emphasis = MenuBlockEmphasis.Muted,
                ),
                onClick = onExit,
                modifier = blockModifier,
            )
        },
    )
}
