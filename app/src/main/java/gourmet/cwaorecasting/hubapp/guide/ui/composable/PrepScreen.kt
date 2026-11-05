package gourmet.cwaorecasting.hubapp.guide.ui.composable

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import gourmet.cwaorecasting.hubapp.BuildConfig
import gourmet.cwaorecasting.hubapp.guide.data.model.UrlState
import gourmet.cwaorecasting.hubapp.guide.ui.viewmodel.PrepViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PrepScreen(
    modifier: Modifier = Modifier,
    viewModel: PrepViewModel = koinViewModel(),
    onNavigateToWebview: () -> Unit,
    onFallbackNavigation: () -> Unit,
) {
    val context = LocalContext.current
    val urlState by viewModel.urlState.collectAsState()

    //region FIREBASE
    var permissionResolvedState by remember {
        mutableStateOf(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
    }

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                permissionResolvedState = true
            }
        )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (isGranted) {
                permissionResolvedState = true
            } else {
                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
    //endregion

    LaunchedEffect(permissionResolvedState) { //FIREBASE: ключ permissionResolvedState, без интеграции LaunchedEffect(Unit)
        if (!permissionResolvedState) return@LaunchedEffect //FIREBASE
        if (!BuildConfig.Test && emulatorCheck()) {
            onFallbackNavigation()
        }
    }

    LaunchedEffect(urlState, permissionResolvedState) { //FIREBASE: доп. ключ permissionResolvedState, без интеграции LaunchedEffect(urlState)
        if (!permissionResolvedState) return@LaunchedEffect //FIREBASE
        when (urlState) {
            UrlState.NoUrl, UrlState.Loading -> Unit
            UrlState.Denied -> {
                onFallbackNavigation()
            }

            is UrlState.Present -> {
                onNavigateToWebview()
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

private fun emulatorCheck(): Boolean {
    return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.PRODUCT.contains("sdk_google")
            || Build.PRODUCT.contains("google_sdk")
            || Build.PRODUCT.contains("sdk")
            || Build.PRODUCT.contains("sdk_x86")
            || Build.PRODUCT.contains("sdk_gphone64_arm64")
            || Build.PRODUCT.contains("vbox86p")
            || Build.PRODUCT.contains("emulator")
            || Build.PRODUCT.contains("simulator");
}