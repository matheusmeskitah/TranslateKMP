package voice_to_text.presentation

sealed class VoiceToTextEvent {
    data object Close : VoiceToTextEvent()
    data object Reset : VoiceToTextEvent()
    data class PermissionResult(
        val isGranted: Boolean,
        val isPermanentlyDeclined: Boolean
    ) : VoiceToTextEvent()

    data class ToggleRecording(val languageCode: String, val errorMsg: String) : VoiceToTextEvent()
}
