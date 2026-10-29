package gourmet.cwaorecasting.hubapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import gourmet.cwaorecasting.hubapp.ui.composable.approot.AppRoot
import gourmet.cwaorecasting.hubapp.ui.theme.GTENPAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GTENPAppTheme {
                AppRoot()
            }
        }
    }
}
