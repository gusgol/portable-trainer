package tech.gusgol.portabletrainer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import tech.gusgol.portabletrainer.ui.home.HomeRoute
import tech.gusgol.portabletrainer.ui.home.HomeViewModel
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutRoute
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutViewModel
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailRoute
import tech.gusgol.portabletrainer.ui.workouts.detail.WorkoutDetailViewModel
import tech.gusgol.portabletrainer.ui.workouts.navigation.workoutsGraph

@Composable
fun PortableTrainerNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = PortableTrainerDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(PortableTrainerDestinations.HOME_ROUTE) {
            HomeRoute(navController)
        }
        composable(PortableTrainerDestinations.WORKOUT_CREATE_ROUTE) {
            CreateWorkoutRoute(navController)
        }
        workoutsGraph { navController.popBackStack() }
    }
}