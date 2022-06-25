package tech.gusgol.portabletrainer.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import tech.gusgol.portabletrainer.ui.navigation.TOP_LEVEL_DESTINATIONS
import tech.gusgol.portabletrainer.ui.navigation.TopLevelDestination


@Composable
fun PTBottomNavigation(
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        },
                        contentDescription = stringResource(id = destination.iconTextId)
                    )
                },
                selected = selected,
                onClick = {
                    onNavigateToTopLevelDestination(destination)
                }
            )
        }
    }
}