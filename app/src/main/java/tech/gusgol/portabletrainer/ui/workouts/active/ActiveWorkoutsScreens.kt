package tech.gusgol.portabletrainer.ui.workouts.active

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tech.gusgol.core.model.Workout
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.navigation.PortableTrainerDestinations
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkDetailDestination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutsScreen(
    navController: NavController,
    activeWorkoutsUiState: ActiveWorkoutsUiState
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
            if (activeWorkoutsUiState is ActiveWorkoutsUiState.Success) {
                SmallFloatingActionButton(
                    onClick = {
                        navController.navigate(PortableTrainerDestinations.WORKOUT_CREATE_ROUTE)
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
            when (activeWorkoutsUiState) {
                ActiveWorkoutsUiState.Error -> WorkoutsErrorScreen()
                ActiveWorkoutsUiState.Loading -> WorkoutsLoadingScreen()
                is ActiveWorkoutsUiState.Empty -> WorkoutsEmptyScreen(navController)
                is ActiveWorkoutsUiState.Success -> WorkoutsListScreen(activeWorkoutsUiState.workouts) {
                    navController.navigate("${WorkDetailDestination.route}/${it.uid}")
                }
            }
        }
    }
}

@Composable
fun WorkoutsErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.error_retrieve_workouts),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WorkoutsLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun WorkoutsEmptyScreen(navController: NavController) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState),
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.img_home),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopStart,
                modifier = Modifier
                    .height(448.dp)
                    .padding(top = 112.dp)
                    .padding(start = 16.dp)
            )

            val headline = buildAnnotatedString {
                append("Please create\nyour first\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("workout!")
                }
            }
            Text(
                headline,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
        FilledTonalButton(
            onClick = {
                navController.navigate(PortableTrainerDestinations.WORKOUT_CREATE_ROUTE)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
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
