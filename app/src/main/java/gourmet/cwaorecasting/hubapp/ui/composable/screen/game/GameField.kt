package gourmet.cwaorecasting.hubapp.ui.composable.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCardItem
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.cardFeedback
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.card.MemoryCard
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.field.shell.GameFieldShell
import gourmet.cwaorecasting.hubapp.ui.composable.screen.game.field.shell.gameFieldShellPadding
import gourmet.cwaorecasting.hubapp.ui.theme.LocalDimens
import gourmet.cwaorecasting.hubapp.data.model.MemoryCard as MemoryCardModel

@Composable
fun GameField(
    cards: List<MemoryCardModel>,
    rows: Int,
    columns: Int,
    round: Int,
    onCardClick: (cardId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gap = LocalDimens.current.itemSpacing
    val shellPadding = gameFieldShellPadding()

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val cardSize = minOf(
            (maxWidth - shellPadding * 2 - gap * (columns - 1)) / columns,
            (maxHeight - shellPadding * 2 - gap * (rows - 1)) / rows,
        )

        GameFieldShell {
            Column(verticalArrangement = Arrangement.spacedBy(gap)) {
                repeat(rows) { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(gap)) {
                        repeat(columns) { column ->
                            val index = row * columns + column
                            val card = cards[index]
                            MemoryCard(
                                item = MemoryCardItem(
                                    symbol = CardSymbols[card.symbolIndex],
                                    state = card.state,
                                ),
                                onClick = { onCardClick(card.id) },
                                modifier = Modifier
                                    .size(cardSize)
                                    .cardFeedback(state = card.state, index = index, round = round),
                            )
                        }
                    }
                }
            }
        }
    }
}
