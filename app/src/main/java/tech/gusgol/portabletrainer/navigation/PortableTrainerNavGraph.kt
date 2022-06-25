package tech.gusgol.portabletrainer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tech.gusgol.portabletrainer.ui.workouts.active.ActiveWorkoutsRoute
import tech.gusgol.portabletrainer.ui.workouts.archived.ArchivedWorkoutsRoute
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutRoute
import tech.gusgol.portabletrainer.ui.workouts.navigation.workoutsGraph

@Composable
fun PortableTrainerNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = PortableTrainerDestinations.WORKOUTS_ACTIVE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(PortableTrainerDestinations.WORKOUTS_ACTIVE) {
            ActiveWorkoutsRoute(navController)
        }
        composable(PortableTrainerDestinations.WORKOUTS_ARCHIVED) {
            ArchivedWorkoutsRoute(navController)
        }
//        composable(PortableTrainerDestinations.HOME_ROUTE) {
//            HomeRoute(navController)
//        }
        composable(PortableTrainerDestinations.WORKOUT_CREATE_ROUTE) {
            CreateWorkoutRoute(navController)
        }
        workoutsGraph { navController.popBackStack() }
    }
}