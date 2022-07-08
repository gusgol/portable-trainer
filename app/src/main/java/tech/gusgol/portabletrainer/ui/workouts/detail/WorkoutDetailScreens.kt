package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import tech.gusgol.core.model.Exercise
import tech.gusgol.core.ui.PTOutlinedTextField
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@Composable
fun WorkoutDetailScreen(
    detailUiState: WorkoutDetailUiState,
    onSubmit: (String, Int?, Int?, Int?) -> Unit,
    onBackClick: () -> Unit,
    onArchiveClick: () -> Unit
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }

    val addExerciseBottomSheet = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val openArchiveDialog = remember { mutableStateOf(false) }

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
                    if (detailUiState.isArchived()) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Unarchive,
                                contentDescription = "Unarchive"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                openArchiveDialog.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Archive,
                                contentDescription = "Archive"
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            if (addExerciseBottomSheet.currentValue == ModalBottomSheetValue.Hidden) {
                SmallFloatingActionButton(
                    onClick = {
                        toggleExerciseSheet(coroutineScope, addExerciseBottomSheet)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    ),
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Create workout")
                }
            }
        }
    ) { innerPadding ->
        ModalBottomSheetLayout(
            sheetState = addExerciseBottomSheet,
            sheetElevation = 8.dp,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    var name by rememberSaveable { mutableStateOf("") }
                    var sets by rememberSaveable { mutableStateOf("") }
                    var reps by rememberSaveable { mutableStateOf("") }
                    var weight by rememberSaveable { mutableStateOf("") }
                    var isFormValid by remember { mutableStateOf(true) }

                    val submit = {
                        if (name.isNotBlank()) {
                            onSubmit(name, sets.toIntOrNull(), reps.toIntOrNull(), weight.toIntOrNull())
                            toggleExerciseSheet(coroutineScope, addExerciseBottomSheet)
                            name = ""
                            sets = ""
                            reps = ""
                            weight = ""
                        } else {
                            isFormValid = false
                        }
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
                        isError = !isFormValid,
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

    if (openArchiveDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openArchiveDialog.value = false
            },
            text = {
                Text("Are you sure you want do archive this workout?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openArchiveDialog.value = false
                        onArchiveClick()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openArchiveDialog.value = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun ExercisesListScreen(exercises: List<Exercise>) {
    if (exercises.isEmpty()) {
        ExercisesEmptyScreen()
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(exercises) { exercise ->
                ExerciseCard(exercise = exercise)
            }
        }
    }
}

@Composable
fun ExercisesEmptyScreen() {
    val scrollableState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState),
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_workout2),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        val headline = buildAnnotatedString {
            append("You haven't added\nany ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("exercises")
            }
            append(" ")
            append("yet!")
        }
        Text(
            headline,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
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
            Column(
                modifier = Modifier
                    .weight(1.0f)
                    .align(CenterVertically)
            ) {
                Text(text = "Name", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                Text(text = exercise.name, style = MaterialTheme.typography.bodyLarge)
            }
            ExerciseInfo(name = "Sets", value = exercise.sets.toString())
            Spacer(Modifier.width(8.dp))
            ExerciseInfo(name = "Reps", value = exercise.reps.toString())
            Spacer(Modifier.width(8.dp))
            ExerciseInfo(name = "Wgt.", value = exercise.weight.toString())
        }
    }
}

@Composable
fun ExerciseInfo(name: String, value: String) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .size(48.dp, 48.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
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
    bottomSheetState: ModalBottomSheetState
) {
    coroutineScope.launch {
        if(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisesEmptyScreenPreview() {
    PortableTrainerTheme {
        ExercisesEmptyScreen()
    }
}