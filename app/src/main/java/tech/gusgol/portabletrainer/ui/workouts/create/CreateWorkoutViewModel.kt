package tech.gusgol.portabletrainer.ui.workouts.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.gusgol.core.data.Result
import tech.gusgol.core.data.domain.InsertWorkoutUseCase
import tech.gusgol.core.model.Workout
import tech.gusgol.core.model.WorkoutIcon

class CreateWorkoutViewModel(
    private val insertWorkoutUseCase: InsertWorkoutUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CreateWorkoutUiState> =
        MutableStateFlow(CreateWorkoutUiState.Idle)
    val uiState: StateFlow<CreateWorkoutUiState> = _uiState.asStateFlow()

    fun insertWorkout(
        name: String,
        icon: WorkoutIcon
    ) {
        _uiState.value = CreateWorkoutUiState.Loading
        val workout = Workout(name = name, icon = icon)
        viewModelScope.launch {
            val result = insertWorkoutUseCase(workout)
            _uiState.value = when(result) {
                is Result.Success -> CreateWorkoutUiState.Success(result.data)
                is Result.Error -> CreateWorkoutUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(
            insertWorkoutUseCase: InsertWorkoutUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CreateWorkoutViewModel(insertWorkoutUseCase) as T
            }
        }
    }
}

sealed interface CreateWorkoutUiState {
    data class Success(val uid: String) : CreateWorkoutUiState
    object Error : CreateWorkoutUiState
    object Loading : CreateWorkoutUiState
    object Idle: CreateWorkoutUiState
}

