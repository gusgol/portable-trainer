package tech.gusgol.portabletrainer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.ui.workouts.navigation.ActiveWorkoutsDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.ArchivedWorkoutsWorkoutDestination

class TopLevelNavigation(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

data class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = ActiveWorkoutsDestination.route,
        selectedIcon = Icons.Filled.FitnessCenter,
        unselectedIcon = Icons.Outlined.FitnessCenter,
        iconTextId = R.string.title_nav_active_workouts
    ),
    TopLevelDestination(
        route = ArchivedWorkoutsWorkoutDestination.route,
        selectedIcon = Icons.Filled.Archive,
        unselectedIcon = Icons.Outlined.Archive,
        iconTextId = R.string.title_nav_active_workouts
    )
)