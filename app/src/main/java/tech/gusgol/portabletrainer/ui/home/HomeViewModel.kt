package tech.gusgol.portabletrainer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tech.gusgol.core.data.domain.ObserveWorkoutsUseCase
import tech.gusgol.core.model.Workout
import javax.inject.Inject

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Error : HomeUiState
    object Empty : HomeUiState
    data class Success(
        val workouts: List<Workout>
    ) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeWorkoutsUseCase: ObserveWorkoutsUseCase
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = observeWorkoutsUseCase()
        .map { workouts ->
            when {
                workouts.isNotEmpty() -> HomeUiState.Success(workouts)
                workouts.isEmpty() -> HomeUiState.Empty
                else -> HomeUiState.Error
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            HomeUiState.Loading
        )
}