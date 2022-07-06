package tech.gusgol.portabletrainer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import tech.gusgol.portabletrainer.navigation.BottomNavigation
import tech.gusgol.portabletrainer.navigation.TopLevelNavigation
import tech.gusgol.portabletrainer.navigation.PortableTrainerNavGraph
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme
import tech.gusgol.portabletrainer.ui.workouts.navigation.ActiveWorkoutsDestination
import tech.gusgol.portabletrainer.ui.workouts.navigation.ArchivedWorkoutsWorkoutDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortableTrainerApp() {
    PortableTrainerTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
        }

        val navController = rememberNavController()
        val topLevelNavigation = remember(navController) {
            TopLevelNavigation(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            bottomBar = {
                if (currentDestination?.route == ActiveWorkoutsDestination.route ||
                    currentDestination?.route == ArchivedWorkoutsWorkoutDestination.route
                ) {
                    BottomNavigation(topLevelNavigation::navigateTo, currentDestination)
                }
            }
        ) { innerPadding ->
            Box(
                Modifier.padding(innerPadding)
            ){
                PortableTrainerNavGraph(
                    navController = navController
                )
            }
        }
    }
}