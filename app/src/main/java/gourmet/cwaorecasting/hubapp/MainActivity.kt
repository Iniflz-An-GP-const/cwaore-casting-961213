package gourmet.cwaorecasting.hubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import gourmet.cwaorecasting.hubapp.ui.composable.approot.AppRoot
import gourmet.cwaorecasting.hubapp.ui.theme.GTENPAppTheme
import gourmet.cwaorecasting.hubapp.ui.theme.Primary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Primary.toArgb()))
        setContent {
            GTENPAppTheme {
                AppRoot()
            }
        }
    }
}
