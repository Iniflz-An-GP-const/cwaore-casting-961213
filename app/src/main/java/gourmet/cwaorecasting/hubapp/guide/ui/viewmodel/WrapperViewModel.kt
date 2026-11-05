package gourmet.cwaorecasting.hubapp.guide.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import gourmet.cwaorecasting.hubapp.BuildConfig
import gourmet.cwaorecasting.hubapp.guide.data.model.UrlState
import gourmet.cwaorecasting.hubapp.guide.data.repository.WrapperRepository

class WrapperViewModel(
    private val wrapperRepository: WrapperRepository,
) : ViewModel() {

    val urlState: StateFlow<UrlState> = MutableStateFlow(
        UrlState.urlStateFromValue(wrapperRepository.getUrl())
    ).asStateFlow()

    val installIdState: StateFlow<String?> = MutableStateFlow(
        wrapperRepository.getId()
    ).asStateFlow()

    val connectionState: StateFlow<Boolean> = wrapperRepository.connectionState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = wrapperRepository.isCurrentlyConnected(),
        )

    private val _showPromoCardState = MutableStateFlow(false)
    val showPromoCardState: StateFlow<Boolean> = _showPromoCardState.asStateFlow()

    fun onPageLoaded(context: Context, isConnected: Boolean) {
        if (BuildConfig.PwaTemplate && isConnected) {
            _showPromoCardState.value = true
        }
    }

    fun dismissPromoCard() {
        _showPromoCardState.value = false
    }
}
