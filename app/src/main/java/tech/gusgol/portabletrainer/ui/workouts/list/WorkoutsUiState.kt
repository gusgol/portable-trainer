package tech.gusgol.portabletrainer.ui.workouts.list

import tech.gusgol.core.model.Workout

sealed interface WorkoutsUiState {
    object Loading : WorkoutsUiState
    object Error : WorkoutsUiState
    object Empty : WorkoutsUiState
    data class Success(
        val workouts: List<Workout>
    ) : WorkoutsUiState
}