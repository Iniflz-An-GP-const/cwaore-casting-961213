package gourmet.cwaorecasting.hubapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import gourmet.cwaorecasting.hubapp.guide.worker.VisitRequestWorker
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
        intent?.let { handleNotificationIntent(it) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        handleNotificationIntent(intent)
    }

    //region FIREBASE
    private fun handleNotificationIntent(currentIntent: Intent) {
        val isFromNotification = currentIntent.getBooleanExtra("ARG_FROM_NOTIFICATION", false)

        if (!isFromNotification) {
            return
        }

        currentIntent.removeExtra("ARG_FROM_NOTIFICATION")

        val pushId = currentIntent.getStringExtra("KEY_PUSH_ID") ?: "unknown_id"
        val linkUrl = currentIntent.getStringExtra("KEY_LINK_URL")
        val uri = currentIntent.data

        if (uri != null) {
            scheduleClickTracking(pushId = pushId, linkUrl = linkUrl)
            currentIntent.data = null
            openExternalBrowser(uri)
        } else {
            scheduleClickTracking(pushId = pushId, linkUrl = null)
        }
    }

    // отправляем запрос о клике по push-уведомлению в фоне, не блокируя MainActivity.
    private fun scheduleClickTracking(pushId: String, linkUrl: String?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val inputData = workDataOf(
            "KEY_PUSH_ID" to pushId,
            "KEY_LINK_URL" to linkUrl
        )

        val trackRequest = OneTimeWorkRequestBuilder<VisitRequestWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(trackRequest)
    }

    private fun openExternalBrowser(uri: Uri) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)

            val chooserIntent = Intent.createChooser(browserIntent, "Open link with:").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            startActivity(chooserIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //endregion
}

