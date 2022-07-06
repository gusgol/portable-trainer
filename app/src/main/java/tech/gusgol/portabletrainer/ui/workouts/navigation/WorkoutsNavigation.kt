package tech.gusgol.portabletrainer.ui.workouts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.gusgol.portabletrainer.navigation.NavigationDestination
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailRoute

const val WORKOUT = "workout"

object CreateWorkoutDestination: NavigationDestination {
    override val route: String = "workout/create"
}

object ActiveWorkoutsDestination: NavigationDestination {
    override val route: String = "workouts/active"
}
object ArchivedWorkoutsWorkoutDestination: NavigationDestination {
    override val route: String = "workouts/archived"
}

object WorkoutDetailDestination : NavigationDestination {
    override val route: String = "workout/detail"
    const val workoutIdArg = "workoutId"
}

fun NavGraphBuilder.workoutDetailGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = "${WorkoutDetailDestination.route}/{${WorkoutDetailDestination.workoutIdArg}}",
        arguments = listOf(
            navArgument(WorkoutDetailDestination.workoutIdArg) {
                type = NavType.StringType
            }
        )
    ) {
        WorkoutDetailRoute(onBackClick)
    }
}