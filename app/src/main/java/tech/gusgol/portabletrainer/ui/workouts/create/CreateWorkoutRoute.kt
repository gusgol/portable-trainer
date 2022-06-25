package tech.gusgol.portabletrainer.ui.workouts.create

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tech.gusgol.portabletrainer.ui.navigation.PortableTrainerDestinations
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkDetailDestination

@Composable
fun CreateWorkoutRoute(
    navController: NavController,
    createWorkoutViewModel: CreateWorkoutViewModel = hiltViewModel()
) {
    val createState by createWorkoutViewModel.uiState.collectAsState()

    (createState as? CreateWorkoutUiState.Success)?.let {
        LaunchedEffect(Unit) {
            navController.navigate("${WorkDetailDestination.route}/${it.uid}") {
                popUpTo(PortableTrainerDestinations.WORKOUTS_ACTIVE)
            }
        }
    }

    CreateWorkoutScreen(
        createState,
        createWorkoutViewModel::insertWorkout
    )
}