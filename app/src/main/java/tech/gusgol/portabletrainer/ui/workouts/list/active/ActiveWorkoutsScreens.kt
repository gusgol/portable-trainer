package tech.gusgol.portabletrainer.ui.workouts.list.active

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.gusgol.core.model.Workout
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsErrorScreen
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsLoadingScreen
import tech.gusgol.portabletrainer.ui.workouts.list.WorkoutsUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutsScreen(
    workoutsUiState: WorkoutsUiState,
    onCreateWorkout: () -> Unit,
    onCardClicked: (Workout) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.title_nav_active_workouts),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        floatingActionButton = {
            if (workoutsUiState is WorkoutsUiState.Success) {
                SmallFloatingActionButton(
                    onClick = {
                        onCreateWorkout()
                    },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Create workout")
                }
            }
        }
    ) { innerPadding ->
        Box(
            Modifier.padding(innerPadding)
        ) {
            when (workoutsUiState) {
                WorkoutsUiState.Error -> WorkoutsErrorScreen()
                WorkoutsUiState.Loading -> WorkoutsLoadingScreen()
                is WorkoutsUiState.Empty -> WorkoutsEmptyScreen(onCreateWorkout)
                is WorkoutsUiState.Success -> WorkoutsListScreen(workoutsUiState.workouts, onCardClicked)
            }
        }
    }
}

@Composable
fun WorkoutsEmptyScreen(
    onCallToActionClick: () -> Unit
) {
    val scrollableState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState),
    ) {
        val headline = buildAnnotatedString {
            append("Please create your\nfirst ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("workout!")
            }
        }
        Text(
            headline,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(id = R.drawable.img_workout),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(448.dp)
        )

        FilledTonalButton(
            onClick = {
                onCallToActionClick()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                "Create", style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun WorkoutsListScreen(
    workouts: List<Workout>,
    onCardClicked: (Workout) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(workouts) { workout ->
            WorkoutCard(workout, onCardClicked)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCard(
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
                    text = "10 exercises",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutsEmptyScreenPreview() {
    PortableTrainerTheme {
        WorkoutsEmptyScreen {}
    }
}
