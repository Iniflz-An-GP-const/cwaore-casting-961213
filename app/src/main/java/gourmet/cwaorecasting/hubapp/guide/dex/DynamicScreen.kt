package gourmet.cwaorecasting.hubapp.guide.dex

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DynamicScreen(
    url: String, // URL, который будет загружаться в WebView
    agent: String, // Настройки User Agent
    isConnected: Boolean,
    onPageLoaded: (() -> Unit), // Кол-бэк, вызываемый после загрузки страницы в WebView
) {
    val currentOnPageLoaded by rememberUpdatedState(onPageLoaded)

    var isFirstPageLoaded by rememberSaveable { mutableStateOf(false) }
    val webViewStateBundle = rememberSaveable { Bundle() }

    var isLoading by rememberSaveable { mutableStateOf(true) }
    var canGoBack by remember { mutableStateOf(false) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    // Set when a load is cancelled because connectivity dropped, so the WebView can be
    // reloaded once connectivity returns instead of staying blank forever.
    var loadInterrupted by rememberSaveable { mutableStateOf(false) }

    // Обработка "шагов назад" в WebView
    BackHandler(enabled = canGoBack) {
        webView?.goBack()
    }

    Box(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.Center,
    ) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    // Настройки WebView
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.databaseEnabled = true
                    settings.cacheMode = WebSettings.LOAD_DEFAULT

                    settings.userAgentString = agent

                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                    cookieManager.setAcceptThirdPartyCookies(this, true)

                    webViewClient = object : WebViewClient() {

                        override fun onPageFinished(view: WebView?, loadedUrl: String?) {
                            canGoBack = view?.canGoBack() == true
                            isLoading = false
                            view?.saveState(webViewStateBundle)
                            
                            // Принудительно сохраняем cookie
                            CookieManager.getInstance().flush()

                            if (!isFirstPageLoaded) {
                                isFirstPageLoaded = true

                                // Вызов кол-бэка после загрузки самой первой страницы.
                                // Используется при интеграции PWA
                                currentOnPageLoaded()
                            }
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            if (request?.isForMainFrame == true) {
                                error?.errorCode == ERROR_HOST_LOOKUP
                            }
                            if (!isConnected) {
                                view?.stopLoading()
                                loadInterrupted = true
                            }
                        }
                    }

                    if (webViewStateBundle.isEmpty) {
                        loadUrl(
                            url,
                            mapOf("X-Requested-With" to "") // Ставим в качестве значения данного заголовка пустую строку
                        )
                    } else {
                        restoreState(webViewStateBundle)
                        if (!isFirstPageLoaded) {
                            isFirstPageLoaded = true
                            isLoading = false
                            currentOnPageLoaded() // Вызываем кол-бэк, если страница была восстановлена из сохранённого состояния
                        }
                    }

                    // WebChromeClient нужен для работы с alert, console.log и другими функциями JS
                    webChromeClient = WebChromeClient()

                    webView = this
                }
            },

            update = { view ->
                if (!isConnected) {
                    view.stopLoading()
                    loadInterrupted = true
                } else if (loadInterrupted && !isFirstPageLoaded) {
                    // Connectivity is back and the first page never finished: retry the load.
                    loadInterrupted = false
                    isLoading = true
                    view.loadUrl(url, mapOf("X-Requested-With" to ""))
                }
            },

            onRelease = { view ->
                view.saveState(webViewStateBundle)
                webView = null
            },

            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (!isConnected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No Internet Connection", color = Color.White, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Please check your connection",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
