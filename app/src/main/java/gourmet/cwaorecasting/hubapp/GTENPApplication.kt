package gourmet.cwaorecasting.hubapp

import android.app.Application
import androidx.work.Configuration
import com.google.firebase.messaging.FirebaseMessaging
import gourmet.cwaorecasting.hubapp.di.appModules
import gourmet.cwaorecasting.hubapp.guide.data.repository.PrepRepository
import gourmet.cwaorecasting.hubapp.guide.di.diModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin

class GTENPApplication : Application(), Configuration.Provider {
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()

    private lateinit var repository: PrepRepository //FIREBASE

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GTENPApplication)
            modules(appModules + diModule)
        }

        repository = GlobalContext.get().get<PrepRepository>() //FIREBASE

        initFcm()
    }

    //region FIREBASE

    // Получение FCM-токена
    private fun initFcm() {
        // Любой сбой FCM обязан разрешиться пустым токеном, иначе fcmToken остаётся null
        // и install висит до самого таймаута
        try {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    val token = try {
                        task.result.orEmpty()
                    } catch (ex: Exception) {
                        ""
                    }

                    repository.onRequestFcmToken(token)
                }
        } catch (ex: Exception) {
            repository.onRequestFcmToken("")
        }
    }
    //endregion

}
