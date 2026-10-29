package gourmet.cwaorecasting.hubapp.ui.composable.screen.difficulty.option

import gourmet.cwaorecasting.hubapp.data.model.Difficulty

data class DifficultyOptionItem(
    val title: String,
    val cardCount: Int,
    val pairCount: Int,
    val rows: Int,
    val columns: Int,
    /** 1-based position of the difficulty among [levelCount] levels. */
    val level: Int,
    val levelCount: Int,
    val selected: Boolean = false,
)

fun Difficulty.toOptionItem(title: String, selected: Boolean = false): DifficultyOptionItem =
    DifficultyOptionItem(
        title = title,
        cardCount = cardCount,
        pairCount = pairCount,
        rows = rows,
        columns = columns,
        level = ordinal + 1,
        levelCount = Difficulty.entries.size,
        selected = selected,
    )
