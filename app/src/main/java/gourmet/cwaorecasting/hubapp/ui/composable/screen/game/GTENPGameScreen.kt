package gourmet.cwaorecasting.hubapp.ui.composable.screen.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.data.model.CardState
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.data.model.GamePhase
import gourmet.cwaorecasting.hubapp.data.model.PauseReason
import gourmet.cwaorecasting.hubapp.ui.composable.approot.topbar.AppTopBar
import gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.titleRes
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.GameHud
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.GameHudItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.shared.ConfirmationDialog
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.ui.viewmodel.GameViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GTENPGameScreen(
    difficulty: Difficulty,
    onMainMenu: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = koinViewModel(parameters = { parametersOf(difficulty) }),
) {
    val dimens = LocalDimens.current
    val state by gameViewModel.state.collectAsState()

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = state.phase != GamePhase.Finished) { showExitDialog = true }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        gameViewModel.setPaused(PauseReason.AppInBackground, paused = true)
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        gameViewModel.setPaused(PauseReason.AppInBackground, paused = false)
    }

    LaunchedEffect(showExitDialog) {
        gameViewModel.setPaused(PauseReason.ExitDialog, paused = showExitDialog)
    }

    if (showExitDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.dialog_exit_title),
            message = stringResource(R.string.dialog_exit_message),
            onConfirm = {
                showExitDialog = false
                onBackClick()
            },
            onDismiss = { showExitDialog = false },
        )
    }

    state.result?.takeIf { state.phase == GamePhase.Finished }?.let { result ->
        GameFinishedDialog(
            result = result,
            onPlayAgain = gameViewModel::restart,
            onMainMenu = onMainMenu,
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(difficulty.titleRes()),
            onNavigateBack = { showExitDialog = true },
        )

        GameHud(
            item = GameHudItem(
                moves = state.moves,
                elapsedText = formatElapsed(state.elapsedMs),
                pairsFound = state.cards.count { it.state == CardState.Matched } / 2,
                pairsTotal = difficulty.pairCount,
                isPreview = state.phase == GamePhase.Preview,
                previewSecondsLeft = state.previewSecondsLeft,
                previewFraction = state.previewFraction,
                bestMoves = state.best.bestMoves,
                bestTimeText = state.best.bestTimeMs?.let(::formatElapsed),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.screenPadding, vertical = dimens.contentSpacing),
        )

        GameField(
            cards = state.cards,
            rows = difficulty.rows,
            columns = difficulty.columns,
            round = state.round,
            onCardClick = gameViewModel::onCardClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(dimens.screenPadding),
        )
    }
}
