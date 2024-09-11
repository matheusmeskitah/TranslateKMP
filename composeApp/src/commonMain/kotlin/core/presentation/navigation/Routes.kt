package core.presentation.navigation

const val LANGUAGE_CODE = "languageCode"

sealed class Routes(val route: String) {
    data object Translate : Routes("translate_screen")
    data object Speech : Routes("speech_screen")
}