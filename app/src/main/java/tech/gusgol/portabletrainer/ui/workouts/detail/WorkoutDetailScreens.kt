package tech.gusgol.portabletrainer.ui.workouts.detail

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import tech.gusgol.portabletrainer.PortableTrainerDestinations


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun WorkoutDetailScreen(
    detailUiState: WorkoutDetailUiState,
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
                    coroutineScope.launch {
                        with(bottomSheetScaffoldState.bottomSheetState) {
                            if (isCollapsed) {
                                expand()
                            } else {
                                collapse()
                            }
                        }
                    }
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var name by rememberSaveable { mutableStateOf("") }
                    var reps by rememberSaveable { mutableStateOf("") }
                    var weight by rememberSaveable { mutableStateOf("") }
                    var sets by remember { mutableStateOf(0f) }

                    SmallTopAppBar(
                        title = {
                            Text("Add exercise", style = MaterialTheme.typography.titleMedium)
                        },
                        actions = {
                            IconButton(onClick = { /* doSomething() */ }) {
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

                    Column(
                        modifier = Modifier.padding(top = 8.dp).padding(horizontal = 16.dp)
                    ) {
//                            Text(text = sets.toString())
                        Text("Sets", style = MaterialTheme.typography.bodyLarge)
                        Slider(
                            value = sets,
                            onValueChange = { sets = it },
                            valueRange = 1f..10f,
                            onValueChangeFinished = {
                                // launch some business logic update with the state you hold
                                // viewModel.updateSelectedSliderValue(sliderPosition)
                            },
                            steps = 9
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        PTOutlinedTextField(
                            value = reps,
                            onValueChange = { weight = it },
                            label = "Reps",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(8.dp))
                        PTOutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = "Weight",
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            when (detailUiState) {
                is WorkoutDetailUiState.Success -> Text(text = detailUiState.workout.name)
                WorkoutDetailUiState.Error -> Text("Failed to load your workout")
                WorkoutDetailUiState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun PTOutlinedTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}