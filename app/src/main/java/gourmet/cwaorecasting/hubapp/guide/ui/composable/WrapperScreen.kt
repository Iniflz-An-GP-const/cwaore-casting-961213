package gourmet.cwaorecasting.hubapp.guide.ui.composable

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import android.app.Activity
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gourmet.cwaorecasting.hubapp.guide.dex.DynamicScreen
import gourmet.cwaorecasting.hubapp.guide.data.model.UrlState
import gourmet.cwaorecasting.hubapp.guide.ui.composable.pwa_templates.CarouselPromoCard
import gourmet.cwaorecasting.hubapp.guide.ui.viewmodel.WrapperViewModel
import org.koin.androidx.compose.koinViewModel
import gourmet.cwaorecasting.hubapp.BuildConfig

@Composable
fun WrapperScreen(
    modifier: Modifier = Modifier,
    viewModel: WrapperViewModel = koinViewModel(),
    onFallbackNavigation: () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current

    // Status bar icons are forced white app-wide (see MainActivity). The WebView content
    // is light, so switch to dark status bar icons while this screen is shown, then restore.
    DisposableEffect(Unit) {
        val window = (view.context as? Activity)?.window
        val controller = window?.let { WindowCompat.getInsetsController(it, view) }
        val previous = controller?.isAppearanceLightStatusBars
        controller?.isAppearanceLightStatusBars = true
        onDispose {
            if (previous != null) controller.isAppearanceLightStatusBars = previous
        }
    }


    val urlState by viewModel.urlState.collectAsStateWithLifecycle()

    // Используйте при наличии PWA, если в ссылке есть параметр запроса install
    val installId by viewModel.installIdState.collectAsStateWithLifecycle()

    val isConnected by viewModel.connectionState.collectAsStateWithLifecycle()

    //region PWA_LAND|PWA_CAROUSEL
    val showPromoCard by viewModel.showPromoCardState.collectAsStateWithLifecycle()

    var isPageLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(urlState) {
        when (urlState) {
            is UrlState.NoUrl, UrlState.Denied -> onFallbackNavigation()
            else -> Unit
        }
    }

    //region PWA_LAND|PWA_CAROUSEL
    LaunchedEffect(isPageLoaded) {
        if (isPageLoaded) {
            viewModel.onPageLoaded(context, isConnected)
        }
    }
    //endregion

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (urlState) {
            UrlState.Loading -> CircularProgressIndicator()

            UrlState.NoUrl, UrlState.Denied -> Unit

            is UrlState.Present -> {
                DynamicScreen (
                    (urlState as UrlState.Present).url,
                    BuildConfig.Agent,
                    isConnected,
                    { isPageLoaded = true }, //кол-бэк, вызываемый внутри функции с WebView
                )

                //region PWA_LAND|PWA_CAROUSEL
//                DismissablePromoCardBanner(
//                    isVisible = showPromoCard,
//                    onDismiss = viewModel::dismissPromoCard,
//                    onPromoClick = {
//                        context.openExternalUrl("ВАША ССЫЛКА")
//                    },
//                    modifier = Modifier
//                        .align(Alignment.TopCenter)
//                        .fillMaxWidth()
//                        .wrapContentWidth(Alignment.CenterHorizontally)
//                )
                //endregion
            }
        }
    }
}

//region PWA_LAND|PWA_CAROUSEL
@Composable
fun DismissablePromoCardBanner(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onPromoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Выезд баннера сверху
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        val dismissState = rememberSwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.Settled,
            positionalThreshold = { it * 0.25f }
        )

        LaunchedEffect(dismissState.currentValue) {
            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                onDismiss()
            }
        }

        // Позволяет смахнуть баннер в сторону
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                //Или LandPromoCard, если указан PWA-ленд
                CarouselPromoCard(
                    onButtonClick = {
                        onDismiss()
                        onPromoClick()
                    }
                )
            }
        }
    }
}

private fun Context.openExternalUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    val chooserIntent = Intent.createChooser(intent, "Choose application")

    try {
        startActivity(chooserIntent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(
            this,
            "No application available to open this link",
            Toast.LENGTH_SHORT
        ).show()
    }
}
//endregion