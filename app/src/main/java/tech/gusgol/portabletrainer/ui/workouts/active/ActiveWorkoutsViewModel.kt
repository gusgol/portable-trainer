package tech.gusgol.portabletrainer.ui.workouts.active

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

sealed interface ActiveWorkoutsUiState {
    object Loading : ActiveWorkoutsUiState
    object Error : ActiveWorkoutsUiState
    object Empty : ActiveWorkoutsUiState
    data class Success(
        val workouts: List<Workout>
    ) : ActiveWorkoutsUiState
}

@HiltViewModel
class ActiveWorkoutsViewModel @Inject constructor(
    observeWorkoutsUseCase: ObserveWorkoutsUseCase
) : ViewModel() {

    val uiState: StateFlow<ActiveWorkoutsUiState> = observeWorkoutsUseCase()
        .map { workouts ->
            when {
                workouts.isNotEmpty() -> ActiveWorkoutsUiState.Success(workouts)
                workouts.isEmpty() -> ActiveWorkoutsUiState.Empty
                else -> ActiveWorkoutsUiState.Error
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ActiveWorkoutsUiState.Loading
        )
}