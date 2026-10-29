package gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.GameHudDefaults
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.hud.GameHudItem
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens

private val BarHeight = 8.dp
private const val TrackAlpha = 0.18f

/** The state updates the fraction every 100 ms; a glide of that length keeps the bar smooth without lagging. */
private const val PreviewStepMs = 100
private const val ProgressStepMs = 400

@Composable
fun GameHudContent(
    item: GameHudItem,
    modifier: Modifier = Modifier,
) {
    val dimens = LocalDimens.current
    val color = LocalContentColor.current

    val target = if (item.isPreview) item.previewFraction else item.pairsFraction
    val fraction by animateFloatAsState(
        targetValue = target,
        animationSpec = tween(
            durationMillis = if (item.isPreview) PreviewStepMs else ProgressStepMs,
            easing = LinearEasing,
        ),
        label = "hudProgress",
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.game_moves_format, item.moves),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(R.string.game_time_format, item.elapsedText),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(BarHeight)
                .clip(CircleShape)
                .background(color.copy(alpha = TrackAlpha)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(color = color, shape = CircleShape),
            )
        }

        Text(
            text = GameHudDefaults.phaseText(item) + if (item.isPreview) "" else " · " + GameHudDefaults.pairsText(
                item
            ),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = GameHudDefaults.bestText(item),
            modifier = Modifier.fillMaxWidth(),
            color = GameHudDefaults.secondaryColor(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}
