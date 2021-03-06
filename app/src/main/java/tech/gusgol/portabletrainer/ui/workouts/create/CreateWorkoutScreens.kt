package tech.gusgol.portabletrainer.ui.workouts.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.gusgol.core.model.WorkoutIcon
import tech.gusgol.portabletrainer.R
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme


@Preview(showBackground = true)
@Composable
fun CreateWorkoutNamePreview() {
    PortableTrainerTheme {
        CreateWorkoutName {}
    }
}

@Preview(showBackground = true)
@Composable
fun CreateWorkoutIconPreview() {
    PortableTrainerTheme {
        CreateWorkoutIcon {}
    }
}

enum class CreateWorkoutSteps {
    NAME, ICON
}

@Composable
fun CreateWorkoutScreen(
    createState: CreateWorkoutUiState,
    onCreateClicked: (String, WorkoutIcon) -> Unit,
    onBackClick: () -> Unit
) {
    var step by remember { mutableStateOf(CreateWorkoutSteps.NAME)}
    var name: String? by rememberSaveable { mutableStateOf(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.action_create_workout),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (step) {
                CreateWorkoutSteps.NAME -> {
                    CreateWorkoutName {
                        name = it
                        step = CreateWorkoutSteps.ICON
                    }
                }
                CreateWorkoutSteps.ICON -> {
                    CreateWorkoutIcon { icon ->
                        name?.let {
                            onCreateClicked(it, icon)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateWorkoutName(
    onNameConfirmed: (String) -> Unit
) {
    val confirm: (String) -> Unit = {
        onNameConfirmed(it.trim())
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        Text(
            stringResource(R.string.msg_what_do_you_want_to_call_it),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            maxLines = 1,
            placeholder = {
                Text(
                    stringResource(R.string.title_workout_name_sample),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(
                onGo = { confirm(text) }
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        text = " "
                    }
                }
        )
        FilledTonalButton(
            onClick = { confirm(text) },
            enabled = text.isNotBlank(),
            modifier = Modifier.padding(top = 32.dp),
        ) {
            Text(
                stringResource(R.string.action_next), style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun CreateWorkoutIcon(
    onIconConfirmed: (WorkoutIcon) -> Unit
) {
    var selected: WorkoutIcon? by rememberSaveable { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.title_pick_icon),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .width(324.dp)
                .padding(horizontal = 40.dp)
        ) {
            items(WorkoutIcon.values()) { item ->
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(48.dp)
                            .background(
                                color = if (selected == item) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.inverseOnSurface
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                selected = item
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = item.icon,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
        FilledTonalButton(
            onClick = {
                selected?.let { onIconConfirmed(it) }
            },
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                stringResource(R.string.action_create), style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}