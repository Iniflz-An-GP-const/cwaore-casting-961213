package gourmet.cwaorecasting.hubapp.ui.composable.screen.main_menu.content.difficulty

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DifficultyBlockLevel(
    val title: String,
    val subtitle: String,
    val rows: Int,
    val columns: Int,
)

data class DifficultyBlockItem(
    val title: String,
    val levels: List<DifficultyBlockLevel>,
    val selectedIndex: Int,
    /** Rows of the tallest level, so the block keeps one height while the levels change. */
    val maxRows: Int,
) {
    val selected: DifficultyBlockLevel get() = levels[selectedIndex]
}

private val MiniCardSize = 10.dp
private val MiniCardGap = 4.dp

/** Height of a field preview with [rows] rows; reserve it for the tallest level. */
internal fun difficultyFieldPreviewHeight(rows: Int): Dp =
    MiniCardSize * rows + MiniCardGap * (rows - 1)

@Composable
internal fun DifficultyFieldPreview(rows: Int, columns: Int, modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(2.dp)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(MiniCardGap)) {
        repeat(rows) {
            Row(horizontalArrangement = Arrangement.spacedBy(MiniCardGap)) {
                repeat(columns) {
                    Box(
                        modifier = Modifier
                            .size(MiniCardSize)
                            .background(color = color, shape = shape),
                    )
                }
            }
        }
    }
}
