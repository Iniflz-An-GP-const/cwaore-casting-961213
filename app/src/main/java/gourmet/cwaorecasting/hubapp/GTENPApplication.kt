package gourmet.cwaorecasting.hubapp

import android.app.Application
import gourmet.cwaorecasting.hubapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class GTENPApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GTENPApplication)
            modules(appModules)
        }
    }
}