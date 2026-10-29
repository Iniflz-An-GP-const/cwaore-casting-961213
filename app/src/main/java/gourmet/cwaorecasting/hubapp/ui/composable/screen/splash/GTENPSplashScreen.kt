package gourmet.cwaorecasting.hubapp.ui.composable.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import gourmet.cwaorecasting.hubapp.ui.composable.screen.splash.SplashContent

@Composable
fun GTENPSplashScreen(
    modifier: Modifier = Modifier,
    onMainMenuScreenNavigation: () -> Unit,
) {
    var isAnimationFinished by remember { mutableStateOf(false) }

    LaunchedEffect(isAnimationFinished) {
        if (isAnimationFinished) {
            onMainMenuScreenNavigation()
        }
    }

    SplashContent(
        modifier = modifier,
        onAnimationFinished = { isAnimationFinished = true },
    )
}