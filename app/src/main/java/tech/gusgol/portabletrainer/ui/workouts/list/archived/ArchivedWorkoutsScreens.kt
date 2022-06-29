package tech.gusgol.portabletrainer.ui.workouts.list.archived

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tech.gusgol.core.model.Workout
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsErrorScreen
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsLoadingScreen
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsUiState
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkDetailDestination
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedWorkoutsScreen(
    navController: NavController,
    workoutsUiState: WorkoutsUiState
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.title_nav_archived_workouts),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when(workoutsUiState) {
                WorkoutsUiState.Error -> WorkoutsErrorScreen()
                WorkoutsUiState.Loading -> WorkoutsLoadingScreen()
                WorkoutsUiState.Loading -> Text("Empty")
                is WorkoutsUiState.Success -> ArchivedWorkoutsList(workoutsUiState.workouts) {
                    navController.navigate("${WorkDetailDestination.route}/${it.uid}")
                }
            }
        }
    }
}

@Composable
fun ArchivedWorkoutsList(
    workouts: List<Workout>,
    onCardClicked: (Workout) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(workouts) { workout ->
            ArchivedWorkoutCard(workout, onCardClicked)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedWorkoutCard(
    workout: Workout,
    onCardClicked: (Workout) -> Unit
) {
    Card(
        onClick = { onCardClicked(workout) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(workout.icon.icon, fontSize = 20.sp)
            }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Archived on ${getFormattedDate(workout.archivedDate)}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArchivedWorkoutCardPreview() {

}

fun getFormattedDate(date: Date?): String {
    return date?.let {
        SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(it)
    } ?: ""
}