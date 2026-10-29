package gourmet.cwaorecasting.hubapp.ui.composable.approot.background

import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.unit.Dp

/** Where the background icons stand and how large they are. A `points/v_*` variant declares one value. */
data class BackgroundPattern(
    val anchors: List<BiasAlignment>,
    val iconSize: Dp,
)

/**
 * Anchors of a regular grid of [columns] x [rows] cells, one anchor in each cell's center.
 * [isFilled] can skip cells (e.g. every other one for a checkerboard); [staggered] shifts every
 * second row by half a cell, with one anchor fewer in it, so the rows interlock like bricks.
 */
fun gridAnchors(
    columns: Int,
    rows: Int,
    staggered: Boolean = false,
    isFilled: (column: Int, row: Int) -> Boolean = { _, _ -> true },
): List<BiasAlignment> = buildList {
    for (row in 0 until rows) {
        val isShiftedRow = staggered && row % 2 == 1
        val count = if (isShiftedRow) columns - 1 else columns
        val firstCenter = if (isShiftedRow) 2f else 1f

        for (column in 0 until count) {
            if (!isFilled(column, row)) continue

            add(
                BiasAlignment(
                    horizontalBias = -1f + (2f * column + firstCenter) / columns,
                    verticalBias = -1f + (2f * row + 1f) / rows,
                ),
            )
        }
    }
}
