package gourmet.cwaorecasting.hubapp.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private const val LightBackgroundLuminance = 0.5f

fun Color.isLight(): Boolean = luminance() > LightBackgroundLuminance

@Composable
fun StatusBarIconsFor(backgroundColor: Color) {
    val view = LocalView.current
    if (view.isInEditMode) return

    val window = view.context.findActivity()?.window ?: return
    val useDarkIcons = backgroundColor.isLight()

    SideEffect {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkIcons
    }
}

@Composable
fun SystemBarsVisibility(visible: Boolean) {
    val view = LocalView.current
    if (view.isInEditMode) return

    val window = view.context.findActivity()?.window ?: return

    DisposableEffect(window, view, visible) {
        val controller = WindowCompat.getInsetsController(window, view)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        if (visible) {
            controller.show(WindowInsetsCompat.Type.systemBars())
        } else {
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }

        onDispose {
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}

private fun Context.findActivity(): Activity? {
    var context: Context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}
