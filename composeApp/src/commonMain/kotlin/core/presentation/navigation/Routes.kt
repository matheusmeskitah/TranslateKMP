package core.presentation.navigation

const val LANGUAGE_CODE = "languageCode"
const val VOICE_RESULT = "voiceResult"

sealed class Routes(val route: String) {
    data object Translate : Routes("translate_screen")
    data object Speech : Routes("speech_screen")
}