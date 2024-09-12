package voice_to_text.domain

import kotlinx.coroutines.flow.StateFlow
import voice_to_text.domain.model.VoiceToTextParserState

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class VoiceToTextParser {
    actual val state: StateFlow<VoiceToTextParserState>
        get() = TODO("Not yet implemented")

    actual fun startListening(languageCode: String, errorMsg: String) {
    }

    actual fun stopListening() {
    }

    actual fun cancel() {
    }

    actual fun reset() {
    }
}