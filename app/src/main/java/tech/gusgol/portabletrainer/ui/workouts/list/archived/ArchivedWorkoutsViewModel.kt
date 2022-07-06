package tech.gusgol.portabletrainer.ui.workouts.list.archived

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import tech.gusgol.core.data.domain.ObserveArchivedWorkoutsUseCase
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsUiState
import javax.inject.Inject


@HiltViewModel
class ArchivedWorkoutsViewModel @Inject constructor(
    observeArchivedWorkoutsUseCase: ObserveArchivedWorkoutsUseCase
): ViewModel() {

    val uiState: StateFlow<WorkoutsUiState> = observeArchivedWorkoutsUseCase()
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