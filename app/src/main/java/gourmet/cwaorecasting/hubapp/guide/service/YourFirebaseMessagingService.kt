package gourmet.cwaorecasting.hubapp.guide.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import gourmet.cwaorecasting.hubapp.MainActivity
import gourmet.cwaorecasting.hubapp.R
import gourmet.cwaorecasting.hubapp.guide.data.repository.PrepRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.text.get

// FIREBASE
class YourFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {
    private val serviceScope = CoroutineScope(SupervisorJob())
    private val repository: PrepRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch {
            repository.onNewFcmToken(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: "New notification"
        val body = remoteMessage.notification?.body ?: ""

        val targetUrl = remoteMessage.data["target_url"]
        val linkUrl = remoteMessage.data["linkUrl"]

        val urlString = when {
            targetUrl != null -> targetUrl
            linkUrl != null -> linkUrl
            else -> null
        }

        val pushId = remoteMessage.data["recordId"] ?: "unknown_id"

        //Посылаем нотификации в качестве реакции на пуш сообщение
        sendNotification(title, body, urlString, pushId)
    }

    private fun sendNotification(title: String, body: String, urlString: String?, pushId: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            data = if (!urlString.isNullOrEmpty()) Uri.parse(urlString) else null

            putExtra("KEY_PUSH_ID", pushId)
            putExtra("KEY_LINK_URL", urlString)

            putExtra("ARG_FROM_NOTIFICATION", true)
        }

        val notificationId = ((urlString ?: "") + pushId).hashCode()

        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, FCM_DEFAULT_CHANNEL)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FCM_DEFAULT_CHANNEL,
                "Important notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        private const val FCM_DEFAULT_CHANNEL = "fcm_default_channel"
    }
}