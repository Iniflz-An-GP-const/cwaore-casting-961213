package gourmet.cwaorecasting.hubapp.guide.data.repository

import android.content.SharedPreferences
import gourmet.cwaorecasting.hubapp.guide.data.INSTALL_ID_KEY
import gourmet.cwaorecasting.hubapp.guide.data.STORED_URL_KEY
import gourmet.cwaorecasting.hubapp.guide.util.NetworkMonitor
import kotlinx.coroutines.flow.Flow

class WrapperRepository(
    private val sharedPreferences: SharedPreferences,
    private val networkMonitor: NetworkMonitor,
) {
    val connectionState: Flow<Boolean> = networkMonitor.isConnected

    // Synchronous snapshot used to seed the connection StateFlow, so the WebView is
    // not told to stopLoading() on the first frame before the flow has emitted.
    fun isCurrentlyConnected(): Boolean = networkMonitor.checkValidated()

    fun getUrl(): String? = sharedPreferences.getString(STORED_URL_KEY, null)

    //PWA_LAND
    fun getId(): String? = sharedPreferences.getString(INSTALL_ID_KEY, null)
}