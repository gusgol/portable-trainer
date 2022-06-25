package tech.gusgol.portabletrainer.ui.workouts.archived

import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.gusgol.portabletrainer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedWorkoutsScreen() {
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
    ) {
    }
}