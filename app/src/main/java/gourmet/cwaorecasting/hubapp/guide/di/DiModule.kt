package gourmet.cwaorecasting.hubapp.guide.di

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import gourmet.cwaorecasting.hubapp.BuildConfig
import gourmet.cwaorecasting.hubapp.guide.data.repository.PrepRepository
import gourmet.cwaorecasting.hubapp.guide.data.repository.WrapperRepository
import gourmet.cwaorecasting.hubapp.guide.data.service.ApiService
import gourmet.cwaorecasting.hubapp.guide.ui.viewmodel.PrepViewModel
import gourmet.cwaorecasting.hubapp.guide.ui.viewmodel.WrapperViewModel
import gourmet.cwaorecasting.hubapp.guide.util.NetworkMonitor
import gourmet.cwaorecasting.hubapp.guide.worker.VisitRequestWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val diModule = module {
    single<CoroutineDispatcher>(named("IO")) { Dispatchers.IO }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.ApiBaseUrl) // см. local.properties -> API_BASE_URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Создание реализации интерфейса ApiService
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    single {
        NetworkMonitor(androidApplication())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "prefix_prefs",
            Context.MODE_PRIVATE
        )
    }

    single {
        PrepRepository(
            context = androidApplication(),
            nApiService = get(),
            sharedPreferences = get(),
            coroutineDispatcher = get(named("IO")),
        )
    }

    viewModel {
        PrepViewModel(
            repository = get(),
        )
    }

    single {
        WrapperRepository(
            sharedPreferences = get(),
            networkMonitor = get(),
        )
    }

    viewModel {
        WrapperViewModel(
            wrapperRepository = get(),
        )
    }

    //region FIREBASE
    worker {
        VisitRequestWorker(
            appContext = get(),
            workerParams = get(),
            service = get()
        )
    }
    //endregion
}