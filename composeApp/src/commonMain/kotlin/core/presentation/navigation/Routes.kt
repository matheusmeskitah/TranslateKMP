package core.presentation.navigation

sealed class Routes(val route: String) {
    data object Translate : Routes("translate_screen")
    data object Speech : Routes("speech_screen")
}