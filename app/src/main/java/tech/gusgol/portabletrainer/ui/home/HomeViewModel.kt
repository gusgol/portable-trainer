package tech.gusgol.portabletrainer.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tech.gusgol.portabletrainer.ErrorMessage
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.data.Result
import tech.gusgol.portabletrainer.domain.GetWorkoutsUseCase
import tech.gusgol.portabletrainer.model.Workout
import java.util.*


sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class Empty(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState

    data class Success(
        val workouts: List<Workout>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val workouts: List<Workout>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList()
) {

    fun toUiState(): HomeUiState =
        if (workouts.isNullOrEmpty()) {
            HomeUiState.Empty(
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        } else {
            HomeUiState.Success(
                workouts = workouts,
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        }
}

class HomeViewModel(
    private val getWorkoutsUseCase: GetWorkoutsUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        getWorkouts()
    }

    fun getWorkouts() {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getWorkoutsUseCase()

            Log.e("Result", result.toString())

            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(workouts = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessage = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.error_retrieve_workouts
                        )
                        it.copy(errorMessages = errorMessage, isLoading = false)
                    }
                }
            }
        }
    }

    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
            getWorkoutsUseCase: GetWorkoutsUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(getWorkoutsUseCase) as T
            }
        }
    }
}