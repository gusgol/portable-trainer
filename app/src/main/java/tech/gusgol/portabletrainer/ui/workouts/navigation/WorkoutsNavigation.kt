package tech.gusgol.portabletrainer.ui.workouts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.gusgol.portabletrainer.navigation.PortableTrainerDestination
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailRoute

object WorkDetailDestination : PortableTrainerDestination {
    override val route: String = "workout/detail"
    const val workoutIdArg = "workoutId"
}

fun NavGraphBuilder.workoutDetailGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = "${WorkDetailDestination.route}/{${WorkDetailDestination.workoutIdArg}}",
        arguments = listOf(
            navArgument(WorkDetailDestination.workoutIdArg) {
                type = NavType.StringType
            }
        )
    ) {
        WorkoutDetailRoute(onBackClick)
    }
}