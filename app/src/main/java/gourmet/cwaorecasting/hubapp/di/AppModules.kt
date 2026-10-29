package gourmet.cwaorecasting.hubapp.di

import androidx.room.Room
import gourmet.cwaorecasting.hubapp.data.database.GTENPAppDatabase
import gourmet.cwaorecasting.hubapp.data.repository.HighScoreRepository
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.ui.viewmodel.GameViewModel
import gourmet.cwaorecasting.hubapp.ui.viewmodel.HighScoreViewModel
import gourmet.cwaorecasting.hubapp.ui.viewmodel.MenuViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private const val DB_NAME = "gtenp_db"

val dataModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = GTENPAppDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<GTENPAppDatabase>().highScoreDao() }

    single {
        HighScoreRepository(
            highScoreDao = get(),
            coroutineDispatcher = get(DispatchersQualifiers.IO)
        )
    }
}

val viewModule = module {
    viewModel { (difficulty: Difficulty) ->
        GameViewModel(difficulty = difficulty, highScoreRepository = get())
    }

    viewModel { HighScoreViewModel(highScoreRepository = get()) }

    viewModel { MenuViewModel(highScoreRepository = get()) }
}

val appModules: List<Module> = listOf(dispatcherModule, dataModule, viewModule)
