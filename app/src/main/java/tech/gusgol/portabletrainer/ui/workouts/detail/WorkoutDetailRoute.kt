package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun WorkoutDetailRoute(
    workoutDetailViewModel: WorkoutDetailViewModel
) {
    val detailState by workoutDetailViewModel.uiState.collectAsState()
    WorkoutDetailScreen(detailUiState = detailState)
}