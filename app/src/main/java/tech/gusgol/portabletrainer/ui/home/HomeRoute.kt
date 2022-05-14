package tech.gusgol.portabletrainer.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(homeState = uiState, navController = navController)
}
