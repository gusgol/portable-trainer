package tech.gusgol.portabletrainer

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import tech.gusgol.portabletrainer.ui.theme.PortableTrainerTheme

@Composable
fun PortableTrainerApp() {
    PortableTrainerTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
        }

        val navController = rememberNavController()

        PortableTrainerNavGraph(
            navController = navController
        )
    }
}