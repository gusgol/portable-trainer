package tech.gusgol.portabletrainer.ui.workouts.list.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.gusgol.portabletrainer.ui.workouts.navigation.CreateWorkoutDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkoutDetailDestination

@Composable
fun ActiveWorkoutsRoute(
    navController: NavController,
    activeWorkoutsViewModel: ActiveWorkoutsViewModel = hiltViewModel()
) {
    val uiState by activeWorkoutsViewModel.uiState.collectAsState()
    ActiveWorkoutsScreen(
        uiState,
        {
            navController.navigate(CreateWorkoutDestination.route)
        },
        { workout ->
            navController.navigate("${WorkoutDetailDestination.route}/${workout.uid}")
        }
    )
}