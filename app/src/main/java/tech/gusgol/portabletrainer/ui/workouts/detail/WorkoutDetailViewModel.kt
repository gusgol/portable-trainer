package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tech.gusgol.core.data.domain.ObserveWorkoutUseCase
import tech.gusgol.core.model.Workout


class WorkoutDetailViewModel(
    observeWorkoutUseCase: ObserveWorkoutUseCase,
    workoutId: String
) : ViewModel() {

    val uiState: StateFlow<WorkoutDetailUiState> = observeWorkoutUseCase(workoutId)
        .map { workout ->
            workout?.let {
                WorkoutDetailUiState.Success(it)
            } ?: WorkoutDetailUiState.Error
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            WorkoutDetailUiState.Loading
        )

    companion object {
        fun provideFactory(
            observeWorkoutUseCase: ObserveWorkoutUseCase,
            workoutId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WorkoutDetailViewModel(observeWorkoutUseCase, workoutId) as T
            }
        }
    }
}

sealed interface WorkoutDetailUiState {
    data class Success(val workout: Workout) : WorkoutDetailUiState
    object Loading : WorkoutDetailUiState
    object Error : WorkoutDetailUiState
}