package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun WorkoutDetailScreen(
    detailUiState: WorkoutDetailUiState
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when (detailUiState) {
            is WorkoutDetailUiState.Success -> Text(text = detailUiState.workout.name)
            WorkoutDetailUiState.Error -> Text("Failed to load your workout")
            WorkoutDetailUiState.Loading -> CircularProgressIndicator()
        }
    }
}