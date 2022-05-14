package tech.gusgol.portabletrainer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tech.gusgol.portabletrainer.ui.home.HomeRoute
import tech.gusgol.portabletrainer.ui.home.HomeViewModel
import tech.gusgol.portabletrainer.ui.workouts.CreateWorkoutRoute
import tech.gusgol.portabletrainer.ui.workouts.create.CreateWorkoutViewModel

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
            val viewModel: HomeViewModel = viewModel(
                factory = ServiceLocator.Workouts.provideHomeViewModelFactory()
            )

            HomeRoute(homeViewModel = viewModel, navController = navController)
        }
        composable(PortableTrainerDestinations.CREATE_WORKOUT_ROUTE) {
            val viewModel: CreateWorkoutViewModel = viewModel(
                factory = ServiceLocator.Workouts.provideCreateWorkoutViewModelFactory()
            )
            CreateWorkoutRoute(viewModel)
        }
    }
}