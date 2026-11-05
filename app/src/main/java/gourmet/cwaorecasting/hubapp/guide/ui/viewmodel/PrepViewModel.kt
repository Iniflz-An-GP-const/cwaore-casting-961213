package gourmet.cwaorecasting.hubapp.guide.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gourmet.cwaorecasting.hubapp.guide.data.model.UrlState
import gourmet.cwaorecasting.hubapp.guide.data.repository.PrepRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PrepViewModel(
    private val repository: PrepRepository
) : ViewModel() {
    val urlState: StateFlow<UrlState> = repository.integrationDataState
        .map { it.urlState }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UrlState.Loading,
        )

    private var installRequested = false

    init {
        startDataCollectionTimeout()
        observeAndTriggerInstall()
    }

    private fun observeAndTriggerInstall() {
        viewModelScope.launch {
            repository.integrationDataState.collect { state ->
                if (state.isReadyToInstall && !installRequested) {
                    installRequested = true
                    repository.install(state.fcmToken.orEmpty()) //FIREBASE. Если отсутствует - без параметров
                }
            }
        }
    }

    private fun startDataCollectionTimeout() {
        viewModelScope.launch {
            delay(TIMEOUT_MS)

            val state = repository.integrationDataState.value
            if (state.urlState !is UrlState.NoUrl) {
                return@launch
            }

            // Install уже уходил и url не принёс – повтор с теми же данными ничего не изменит
            if (installRequested) {
                repository.storeUrlStateValue(UrlState.ACCESS_DENIED_URL_VALUE)
                return@launch
            }

            // Часть интеграций не уложилась в таймаут: отправляем install с тем,
            // что успело собраться, недостающие поля уходят пустыми
            installRequested = true
            repository.install(state.fcmToken.orEmpty()) //FIREBASE. Если отсутствует - без параметров
        }
    }

    companion object {
        private const val TIMEOUT_MS = 30_000L //не менее 15 секунд
    }
}