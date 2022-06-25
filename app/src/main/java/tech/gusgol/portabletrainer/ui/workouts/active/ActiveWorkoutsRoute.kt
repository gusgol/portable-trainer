package tech.gusgol.portabletrainer.ui.workouts.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ActiveWorkoutsRoute(
    navController: NavController,
    activeWorkoutsViewModel: ActiveWorkoutsViewModel = hiltViewModel()
) {
    val uiState by activeWorkoutsViewModel.uiState.collectAsState()
    ActiveWorkoutsScreen(
        navController,
        uiState
    )
}