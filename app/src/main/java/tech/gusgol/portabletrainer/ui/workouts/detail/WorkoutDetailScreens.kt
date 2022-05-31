package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import tech.gusgol.core.model.Exercise
import tech.gusgol.portabletrainer.ui.home.WorkoutCard
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun WorkoutDetailScreen(
    detailUiState: WorkoutDetailUiState,
    onSubmit: (String, Int?, Int?, Int?) -> Unit,
    onBackClick: () -> Unit
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    if (detailUiState is WorkoutDetailUiState.Success) {
                        Text(
                            text = detailUiState.workout.name,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Archive,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    toggleExerciseSheet(coroutineScope, bottomSheetScaffoldState)
                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Create workout")
            }
        }
    ) { innerPadding ->
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            backgroundColor = MaterialTheme.colorScheme.background,
            sheetContent = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 64.dp)
                ) {
                    var name by rememberSaveable { mutableStateOf("") }
                    var sets by rememberSaveable { mutableStateOf("") }
                    var reps by rememberSaveable { mutableStateOf("") }
                    var weight by rememberSaveable { mutableStateOf("") }

                    val submit = {
                        onSubmit(name, sets.toIntOrNull(), reps.toIntOrNull(), weight.toIntOrNull())
                        toggleExerciseSheet(coroutineScope, bottomSheetScaffoldState)
                        name = ""
                        sets = ""
                        reps = ""
                        weight = ""
                    }

                    SmallTopAppBar(
                        title = {
                            Text("Add exercise", style = MaterialTheme.typography.titleMedium)
                        },
                        actions = {
                            IconButton(onClick = { submit() }) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Save"
                                )
                            }
                        }
                    )
                    PTOutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name",
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        PTOutlinedTextField(
                            value = sets,
                            onValueChange = { sets = it },
                            label = "Sets",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.width(8.dp))
                        PTOutlinedTextField(
                            value = reps,
                            onValueChange = { reps = it },
                            label = "Reps",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.width(8.dp))
                        PTOutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = "Weight",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Go,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions (
                              onGo = { submit() }
                            ),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }

                }
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            when (detailUiState) {
                is WorkoutDetailUiState.Success -> ExercisesListScreen(exercises = detailUiState.exercises)
                WorkoutDetailUiState.Error -> Text("Failed to load your workout")
                WorkoutDetailUiState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ExercisesListScreen(exercises: List<Exercise>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(exercises) { exercise ->
            ExerciseCard(exercise = exercise)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCard(exercise: Exercise) {
    Card(
        onClick = {  },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Text(text = "Name", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                Text(text = exercise.name, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview
@Composable
fun ExerciseCardPreview() {
    PortableTrainerTheme {
        ExerciseCard(exercise = Exercise(
            "", "Pull up", 5, 5, 5, ""
        ))
    }
}


@OptIn(ExperimentalMaterialApi::class)
private fun toggleExerciseSheet(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    coroutineScope.launch {
        with(bottomSheetScaffoldState.bottomSheetState) {
            if (isCollapsed) {
                expand()
            } else {
                collapse()
            }
        }
    }
}

@Composable
fun PTOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        textStyle = LocalTextStyle.current.copy(
            textAlign = textAlign
        )
    )
}