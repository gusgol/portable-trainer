package tech.gusgol.portabletrainer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tech.gusgol.portabletrainer.ui.workouts.list.active.ActiveWorkoutsRoute
import tech.gusgol.portabletrainer.ui.workouts.list.archived.ArchivedWorkoutsRoute
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutRoute
import tech.gusgol.portabletrainer.ui.workouts.navigation.ActiveWorkoutsDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.ArchivedWorkoutsWorkoutDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.CreateWorkoutDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.workoutDetailGraph

@Composable
fun PortableTrainerNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ActiveWorkoutsDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
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
        workoutDetailGraph { navController.popBackStack() }
    }
}