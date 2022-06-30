package tech.gusgol.portabletrainer.ui.workouts.list.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.gusgol.portabletrainer.navigation.PortableTrainerDestinations
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkDetailDestination

@Composable
fun ActiveWorkoutsRoute(
    navController: NavController,
    activeWorkoutsViewModel: ActiveWorkoutsViewModel = hiltViewModel()
) {
    val uiState by activeWorkoutsViewModel.uiState.collectAsState()
    ActiveWorkoutsScreen(
        uiState,
        {
            navController.navigate(PortableTrainerDestinations.WORKOUT_CREATE_ROUTE)
        },
        { workout ->
            navController.navigate("${WorkDetailDestination.route}/${workout.uid}")
        }
    )
}