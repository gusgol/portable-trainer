package tech.gusgol.portabletrainer.ui.workouts.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.gusgol.portabletrainer.PortableTrainerDestination
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailRoute
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailViewModel

object WorkDetailDestination : PortableTrainerDestination {
    override val route: String = "workout/detail"
    const val workoutIdArg = "workoutId"
}

fun NavGraphBuilder.workoutsGraph() {
    composable(
        route = "${WorkDetailDestination.route}/{${WorkDetailDestination.workoutIdArg}}",
        arguments = listOf(
            navArgument(WorkDetailDestination.workoutIdArg) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
//        val workoutId: String =
//            backStackEntry.arguments?.getString(WorkDetailDestination.workoutIdArg).orEmpty()
//        val viewModel: WorkoutDetailViewModel = viewModel(
//            factory = ServiceLocator.Workouts.provideWorkoutDetailViewModelFactory(workoutId)
//        )
        WorkoutDetailRoute()
    }
}