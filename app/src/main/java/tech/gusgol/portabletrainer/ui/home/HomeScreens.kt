package tech.gusgol.portabletrainer.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tech.gusgol.portabletrainer.PortableTrainerDestinations
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.model.Workout
import tech.gusgol.portabletrainer.model.WorkoutIcon
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme
import tech.gusgol.portabletrainer.ui.workouts.navigation.WorkDetailDestination


@Composable
fun HomeScreen(
    homeState: HomeUiState,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Active Workouts",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        floatingActionButton = {
            if (homeState is HomeUiState.Success) {
                SmallFloatingActionButton(
                    onClick = {
                        navController.navigate(PortableTrainerDestinations.WORKOUT_CREATE_ROUTE)
                    },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Create workout")
                }
            }
        },
        bottomBar = {
            BottomNavigation()
        }
    ) {
        when (homeState) {
            is HomeUiState.Empty -> WorkoutsEmptyScreen(navController)
            is HomeUiState.Success -> WorkoutsListScreen(homeState.workouts) {
                navController.navigate("${WorkDetailDestination.route}/${it.uid}")
            }
        }
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
        Button(
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
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(workouts) { workout ->
            WorkoutCard(workout, onCardClicked)
        }
    }

}

@Composable
fun BottomNavigation() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        Pair("Active Workouts", Icons.Filled.FitnessCenter),
        Pair("Archive", Icons.Filled.Archive),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.second, contentDescription = null) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val workout = Workout(
        uid = "",
        name = "Workout 1",
        icon = WorkoutIcon.Biceps
    )
    WorkoutCard(workout = workout) {}
}

@Preview
@Composable
fun Fonts() {
    val dummy = "The lazy fox juumps"
    PortableTrainerTheme {
        Column {
            Text("s")
            Text(dummy, style = MaterialTheme.typography.headlineLarge)
            Text(dummy, style = MaterialTheme.typography.headlineMedium)
            Text(dummy, style = MaterialTheme.typography.headlineSmall)
            Text(dummy, style = MaterialTheme.typography.titleLarge)
            Text(dummy, style = MaterialTheme.typography.titleMedium)
            Text(dummy, style = MaterialTheme.typography.titleSmall)
            Text(dummy, style = MaterialTheme.typography.bodyLarge)
            Text(dummy, style = MaterialTheme.typography.bodyMedium)
            Text(dummy, style = MaterialTheme.typography.bodySmall)
            Text(dummy, style = MaterialTheme.typography.displayLarge)
            Text(dummy, style = MaterialTheme.typography.displayMedium)
            Text(dummy, style = MaterialTheme.typography.displaySmall)
            Text(dummy, style = MaterialTheme.typography.labelLarge)
            Text(dummy, style = MaterialTheme.typography.labelMedium)
            Text(dummy, style = MaterialTheme.typography.labelSmall)
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCardClicked(workout)
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.onTertiary, shape = CircleShape),
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