package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

data class GameHudItem(
    val moves: Int,
    val elapsedText: String,
    val pairsFound: Int,
    val pairsTotal: Int,
    val isPreview: Boolean,
    val previewSecondsLeft: Int,
    /** 1 when the preview starts, 0 when it ends; only meaningful during the preview. */
    val previewFraction: Float,
    val bestMoves: Int?,
    val bestTimeText: String?,
) {
    val pairsFraction: Float get() = if (pairsTotal == 0) 0f else pairsFound.toFloat() / pairsTotal
}

internal object GameHudDefaults {
    const val SECONDARY_ALPHA: Float = 0.7f

    val shape: Shape
        @Composable get() = RoundedCornerShape(LocalDimens.current.cardCornerRadius)

    @Composable
    fun phaseText(item: GameHudItem): String =
        if (item.isPreview) {
            stringResource(R.string.game_preview_format, item.previewSecondsLeft)
        } else {
            stringResource(R.string.game_playing_hint)
        }

    @Composable
    fun bestText(item: GameHudItem): String {
        val bestMoves = item.bestMoves
        val bestTimeText = item.bestTimeText

        return if (bestMoves != null && bestTimeText != null) {
            stringResource(R.string.game_best_format, bestMoves, bestTimeText)
        } else {
            stringResource(R.string.game_best_none)
        }
    }

    @Composable
    fun pairsText(item: GameHudItem): String =
        stringResource(R.string.game_hud_pairs_format, item.pairsFound, item.pairsTotal)

    @Composable
    fun secondaryColor(): Color = LocalContentColor.current.copy(alpha = SECONDARY_ALPHA)
}

@Composable
internal fun Modifier.gameHudSurface(
    containerColor: Color,
    border: BorderStroke? = null,
): Modifier {
    val shape = GameHudDefaults.shape

    return this
        .clip(shape)
        .background(color = containerColor, shape = shape)
        .let { base -> if (border != null) base.border(border, shape) else base }
        .padding(LocalDimens.current.cardPadding)
}
