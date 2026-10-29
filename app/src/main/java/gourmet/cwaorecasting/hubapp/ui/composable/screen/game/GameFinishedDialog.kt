package gourmet.cwaorecasting.hubapp.ui.composable.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.data.model.GameResult

@Composable
fun GameFinishedDialog(
    result: GameResult,
    onPlayAgain: () -> Unit,
    onMainMenu: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.game_finished_title)) },
        text = {
            Column {
                ResultLine(
                    text = stringResource(R.string.game_finished_moves_format, result.moves),
                    isNewBest = result.isNewBestMoves,
                )
                ResultLine(
                    text = stringResource(
                        R.string.game_finished_time_format,
                        formatElapsed(result.timeMs)
                    ),
                    isNewBest = result.isNewBestTime,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.game_play_again))
            }
        },
        dismissButton = {
            TextButton(onClick = onMainMenu) {
                Text(text = stringResource(R.string.game_main_menu))
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    )
}

@Composable
private fun ResultLine(text: String, isNewBest: Boolean) {
    Text(
        text = if (isNewBest) stringResource(
            R.string.game_finished_new_record_format,
            text
        ) else text,
        color = if (isNewBest) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge,
    )
}
