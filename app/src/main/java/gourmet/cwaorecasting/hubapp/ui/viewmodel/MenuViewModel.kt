package gourmet.cwaorecasting.hubapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gourmet.cwaorecasting.hubapp.data.model.BestScores
import gourmet.cwaorecasting.hubapp.data.model.Difficulty
import gourmet.cwaorecasting.hubapp.data.repository.HighScoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class MenuViewModel(
    highScoreRepository: HighScoreRepository,
) : ViewModel() {

    private val _selectedDifficulty = MutableStateFlow(Difficulty.Easy)
    val selectedDifficulty: StateFlow<Difficulty> = _selectedDifficulty.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val bestScores: StateFlow<BestScores?> = _selectedDifficulty
        .flatMapLatest { difficulty -> highScoreRepository.observeBest(difficulty) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )

    fun selectDifficulty(index: Int) {
        Difficulty.entries.getOrNull(index)
            ?.let { difficulty -> _selectedDifficulty.value = difficulty }
    }
}
