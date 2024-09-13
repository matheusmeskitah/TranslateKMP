import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import core.presentation.navigation.VOICE_RESULT
import di.rememberViewModel
import translate.presentation.TranslateEvent
import translate.presentation.TranslateScreen
import translate.presentation.TranslateViewModel
import voice_to_text.presentation.VoiceToTextEvent
import voice_to_text.presentation.VoiceToTextScreen
import voice_to_text.presentation.VoiceToTextViewModel

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

                    val voiceResult by it
                        .savedStateHandle
                        .getStateFlow<String?>(VOICE_RESULT, null)
                        .collectAsState()
                    LaunchedEffect(voiceResult) {
                        viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                        it.savedStateHandle[VOICE_RESULT] = null
                    }

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
                ) { backStackEntry ->
                    val languageCode = backStackEntry.arguments?.getString(LANGUAGE_CODE) ?: "en"
                    val viewModel by rememberViewModel<VoiceToTextViewModel>()
                    val state by viewModel.state.collectAsState()

                    VoiceToTextScreen(
                        state = state,
                        languageCode = languageCode,
                        onResult = { spokenText ->
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                VOICE_RESULT, spokenText
                            )
                            navController.popBackStack()
                        },
                        onEvent = { event ->
                            when (event) {
                                is VoiceToTextEvent.Close -> {
                                    navController.popBackStack()
                                }

                                else -> viewModel.onEvent(event)
                            }
                        }
                    )
                }
            }
        }
    }
}