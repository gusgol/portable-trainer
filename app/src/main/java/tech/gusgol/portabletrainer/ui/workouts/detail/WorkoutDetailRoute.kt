package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WorkoutDetailRoute(
    onBackClick: () -> Unit,
    workoutDetailViewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    val detailState by workoutDetailViewModel.uiState.collectAsState()
    WorkoutDetailScreen(detailUiState = detailState, workoutDetailViewModel::addExercise, onBackClick)
}