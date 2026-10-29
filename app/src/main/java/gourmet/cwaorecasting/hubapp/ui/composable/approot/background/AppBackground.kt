package gourmet.cwaorecasting.hubapp.ui.composable.approot.background

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.points.AppBackgroundPattern
import gourmet.cwaorecasting.hubapp.ui.composable.approot.background.AppBackgroundDecor

/**
 * The menu background: icons on a grid, in motion. This is the single switch point for both
 * axes: change the `AppBackgroundPattern` import to try another grid (v_grid_dense,
 * v_grid_regular, v_grid_sparse, v_grid_staggered, v_grid_diamond) and the `AppBackgroundDecor`
 * import to try another motion (v_fade, v_rotate, v_pulse, v_wave).
 */
@Composable
fun AppBackground(modifier: Modifier = Modifier) {
    AppBackgroundDecor(pattern = AppBackgroundPattern, modifier = modifier)
}
