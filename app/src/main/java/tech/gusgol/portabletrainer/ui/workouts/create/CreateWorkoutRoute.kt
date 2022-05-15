package tech.gusgol.portabletrainer.ui.workouts.create

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import tech.gusgol.portabletrainer.ui.workouts.CreateWorkoutScreen
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkDetailDestination

@Composable
fun CreateWorkoutRoute(
    createWorkoutViewModel: CreateWorkoutViewModel,
    navController: NavController
) {
    val uiState by createWorkoutViewModel.uiState.collectAsState()
    when (uiState) {
        is CreateWorkoutUiState.Success -> {
            navController.navigate("${WorkDetailDestination.route}/${(uiState as CreateWorkoutUiState.Success).uid}")
        }
        CreateWorkoutUiState.Error -> Log.e("Save", "Error")
        CreateWorkoutUiState.Idle -> Log.e("Save", "Idle")
        CreateWorkoutUiState.Loading -> Log.e("Save", "Loading")
    }
    CreateWorkoutScreen(
        createWorkoutViewModel::insertWorkout
    )
}