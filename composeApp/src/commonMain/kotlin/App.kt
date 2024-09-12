import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import core.presentation.TranslatorTheme
import core.presentation.navigation.LANGUAGE_CODE
import core.presentation.navigation.Routes
import di.rememberViewModel
import translate.presentation.TranslateEvent
import translate.presentation.TranslateScreen
import translate.presentation.TranslateViewModel

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
                        onEvent = { event ->
                            when (event) {
                                is TranslateEvent.RecordAudio -> navController.navigate(Routes.Speech.route + "/${state.fromLanguage.language.langCode}")
                                else -> viewModel.onEvent(event)
                            }
                        }
                    )
                }

                composable(
                    route = Routes.Speech.route + "/{$LANGUAGE_CODE}",
                    arguments = listOf(
                        navArgument(LANGUAGE_CODE) {
                            type = NavType.StringType
                            defaultValue = "en"
                        }
                    )
                ) {

                }
            }
        }
    }
}