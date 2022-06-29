package tech.gusgol.portabletrainer.ui.workouts.list.archived

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ArchivedWorkoutsRoute(
    navController: NavController,
    archivedWorkoutsViewModel: ArchivedWorkoutsViewModel = hiltViewModel()
) {
    val uiState by archivedWorkoutsViewModel.uiState.collectAsState()
    ArchivedWorkoutsScreen(navController, uiState)
}