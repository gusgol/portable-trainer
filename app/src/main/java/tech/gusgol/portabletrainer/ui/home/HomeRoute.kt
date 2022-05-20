package tech.gusgol.portabletrainer.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(homeState = uiState, navController = navController)
}
