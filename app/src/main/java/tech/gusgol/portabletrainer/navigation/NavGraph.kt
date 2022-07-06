package tech.gusgol.portabletrainer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import tech.gusgol.portabletrainer.ui.workouts.navigation.ActiveWorkoutsDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.workoutsGraph

@Composable
fun PortableTrainerNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ActiveWorkoutsDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        workoutsGraph(navController)
    }
}