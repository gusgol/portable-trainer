package tech.gusgol.portabletrainer.ui.workouts.list.active

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tech.gusgol.core.data.domain.ObserveWorkoutsUseCase
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsUiState
import javax.inject.Inject


@HiltViewModel
class ActiveWorkoutsViewModel @Inject constructor(
    observeWorkoutsUseCase: ObserveWorkoutsUseCase
) : ViewModel() {

    val uiState: StateFlow<WorkoutsUiState> = observeWorkoutsUseCase()
        .map { workouts ->
            when {
                workouts.isNotEmpty() -> WorkoutsUiState.Success(workouts)
                workouts.isEmpty() -> WorkoutsUiState.Empty
                else -> WorkoutsUiState.Error
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            WorkoutsUiState.Loading
        )
}