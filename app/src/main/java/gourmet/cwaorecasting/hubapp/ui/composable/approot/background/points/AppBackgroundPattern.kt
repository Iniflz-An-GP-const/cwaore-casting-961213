package gourmet.cwaorecasting.hubapp.ui.composable.approot.background.points

import androidx.compose.ui.unit.dp
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.BackgroundPattern
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.gridAnchors

/** A regular 4 x 8 grid of medium icons. */
val AppBackgroundPattern = BackgroundPattern(
    anchors = gridAnchors(columns = 4, rows = 8),
    iconSize = 30.dp,
)
