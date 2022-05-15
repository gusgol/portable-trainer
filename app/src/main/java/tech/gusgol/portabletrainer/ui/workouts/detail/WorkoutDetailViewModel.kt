package tech.gusgol.portabletrainer.ui.workouts.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import tech.gusgol.portabletrainer.domain.ObserveWorkoutUseCase
import tech.gusgol.portabletrainer.model.Workout


class WorkoutDetailViewModel(
    observeWorkoutUseCase: ObserveWorkoutUseCase,
    workoutId: String
) : ViewModel() {

    val uiState: StateFlow<WorkoutDetailUiState> = observeWorkoutUseCase(workoutId)
        .map {
            if (it != null) {
                Log.e("uiState", it.name)
                WorkoutDetailUiState.Success(it)
            } else {
                WorkoutDetailUiState.Error
            }
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