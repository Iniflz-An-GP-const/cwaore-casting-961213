package gourmet.cwaorecasting.hubapp.guide.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import gourmet.cwaorecasting.hubapp.BuildConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import gourmet.cwaorecasting.hubapp.guide.data.repository.PrepRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Debug-only triggers for exercising [PrepRepository].
 * No-ops unless BuildConfig.TEST is true, so it's safe to keep registered in every build.
 *
 * Trigger with:
 *   adb shell am broadcast -a gbcorp.c341.lizarogame.wrapper.TEST_REFRESH   (resend stored token)
 *   adb shell am broadcast -a gbcorp.c341.lizarogame.wrapper.TEST_ROTATE_TOKEN   (real new token -> backend)
 */
internal class TestRefreshReceiver : BroadcastReceiver(), KoinComponent {
    private val urlRepository: PrepRepository by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (!BuildConfig.Test) return
        // goAsync() keeps the process alive past onReceive() returning — without it the
        // network call can get killed mid-flight before its response logs.
        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                when (intent.action) {
                    ACTION_TEST_ROTATE_TOKEN -> {
                        // deleteToken() forces Firebase to hand out a genuinely new token on the
                        // next getToken() call, unlike forceRefresh() which just resends the
                        // token already on file — that path never proves the backend actually
                        // receives a fresh value.
                        await(FirebaseMessaging.getInstance().deleteToken())
                        val newToken = await(FirebaseMessaging.getInstance().token)
                        urlRepository.onNewFcmToken(newToken)
                    }
                    else -> urlRepository.forceRefresh()
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    private suspend fun <T> await(task: Task<T>): T = suspendCancellableCoroutine { cont ->
        task.addOnSuccessListener { cont.resume(it) }
            .addOnFailureListener { cont.resumeWithException(it) }
    }

    companion object {
        const val ACTION_TEST_REFRESH = "gbcorp.c341.lizarogame.wrapper.TEST_REFRESH"
        const val ACTION_TEST_ROTATE_TOKEN = "gbcorp.c341.lizarogame.wrapper.TEST_ROTATE_TOKEN"
    }
}
