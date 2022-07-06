package tech.gusgol.portabletrainer.ui.workouts.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.gusgol.portabletrainer.ui.workouts.navigation.ActiveWorkoutsDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkoutDetailDestination

@Composable
fun CreateWorkoutRoute(
    navController: NavController,
    createWorkoutViewModel: CreateWorkoutViewModel = hiltViewModel()
) {
    val createState by createWorkoutViewModel.uiState.collectAsState()

    (createState as? CreateWorkoutUiState.Success)?.let {
        LaunchedEffect(Unit) {
            navController.navigate("${WorkoutDetailDestination.route}/${it.uid}") {
                popUpTo(ActiveWorkoutsDestination.route)
            }
        }
    }

    CreateWorkoutScreen(
        createState,
        createWorkoutViewModel::insertWorkout
    ) { navController.popBackStack() }
}