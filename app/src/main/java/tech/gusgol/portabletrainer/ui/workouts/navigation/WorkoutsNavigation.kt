package tech.gusgol.portabletrainer.ui.workouts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.gusgol.portabletrainer.navigation.NavigationDestination
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutRoute
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailRoute
import tech.gusgol.portabletrainer.ui.workouts.list.active.ActiveWorkoutsRoute
import tech.gusgol.portabletrainer.ui.workouts.list.archived.ArchivedWorkoutsRoute

const val WORKOUT = "workout"
const val WORKOUTS = "workouts"

object CreateWorkoutDestination: NavigationDestination {
    override val route: String = "$WORKOUT/create"
}

object ActiveWorkoutsDestination: NavigationDestination {
    override val route: String = "$WORKOUTS/active"
}
object ArchivedWorkoutsWorkoutDestination: NavigationDestination {
    override val route: String = "$WORKOUTS/archived"
}

object WorkoutDetailDestination : NavigationDestination {
    override val route: String = "$WORKOUT/detail"
    const val workoutIdArg = "workoutId"
}

fun NavGraphBuilder.workoutsGraph(
    navController: NavController
) {
    composable(ActiveWorkoutsDestination.route) {
        ActiveWorkoutsRoute(navController)
    }
    composable(ArchivedWorkoutsWorkoutDestination.route) {
        ArchivedWorkoutsRoute(navController)
    }
    composable(CreateWorkoutDestination.route) {
        CreateWorkoutRoute(navController)
    }
    composable(
        route = "${WorkoutDetailDestination.route}/{${WorkoutDetailDestination.workoutIdArg}}",
        arguments = listOf(
            navArgument(WorkoutDetailDestination.workoutIdArg) {
                type = NavType.StringType
            }
        )
    ) {
        WorkoutDetailRoute(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}