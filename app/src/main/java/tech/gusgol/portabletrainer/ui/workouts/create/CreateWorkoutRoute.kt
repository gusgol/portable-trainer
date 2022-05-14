package tech.gusgol.portabletrainer.ui.workouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CreateWorkoutRoute(
    createWorkoutViewModel: CreateWorkoutViewModel
) {
    val uiState by createWorkoutViewModel.uiState.collectAsState()
    CreateWorkoutScreen(
        uiState,
        createWorkoutViewModel::insertWorkout
    )
}