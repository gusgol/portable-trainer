package tech.gusgol.portabletrainer.ui.workouts.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tech.gusgol.core.data.domain.ArchiveWorkoutUseCase
import tech.gusgol.core.data.domain.InsertExerciseUseCase
import tech.gusgol.core.data.domain.ObserveWorkoutWithExercisesUseCase
import tech.gusgol.core.model.Exercise
import tech.gusgol.core.model.Workout
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkoutDetailDestination
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeWorkoutWithExercisesUseCase: ObserveWorkoutWithExercisesUseCase,
    private val insertExerciseUseCase: InsertExerciseUseCase,
    private val archiveWorkoutUseCase: ArchiveWorkoutUseCase
) : ViewModel() {

    private val workoutId: String = checkNotNull(savedStateHandle[WorkoutDetailDestination.workoutIdArg])

    val uiState: StateFlow<WorkoutDetailUiState> = observeWorkoutWithExercisesUseCase(workoutId)
        .map {
            if (it.isNotEmpty()) {
                with (it.first()) {
                    WorkoutDetailUiState.Success(workout, exercises)
                }
            } else {
                WorkoutDetailUiState.Error
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            WorkoutDetailUiState.Loading
        )

    fun addExercise(name: String, sets: Int?, reps: Int?, weight: Int?) {
        val exercise = Exercise(
            name = name,
            workoutId = workoutId,
            sets = sets,
            reps = reps,
            weight = weight
        )
        viewModelScope.launch {
            insertExerciseUseCase(exercise)
        }
    }

    fun archive() {
        (uiState.value as? WorkoutDetailUiState.Success)?.let {
            viewModelScope.launch {
                // TODO simplify this part
                val result = archiveWorkoutUseCase(it.workout)
            }
        }
    }
}

sealed interface WorkoutDetailUiState {
    data class Success(
        val workout: Workout,
        val exercises: List<Exercise>) : WorkoutDetailUiState
    object Loading : WorkoutDetailUiState
    object Error : WorkoutDetailUiState

    fun isArchived(): Boolean {
        if (this is Success) {
            return workout.archived
        } else {
            return false
        }
    }
}