import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.presentation.TranslatorTheme
import translate.di.rememberViewModel
import translate.presentation.TranslateViewModel
import core.presentation.navigation.Routes
import translate.presentation.TranslateScreen

@Composable
fun App() {
    TranslatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Routes.Translate.route
            ) {
                composable(route = Routes.Translate.route) {
                    val viewModel by rememberViewModel<TranslateViewModel>()
                    val state by viewModel.state.collectAsState()

                    TranslateScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}