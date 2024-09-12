package voice_to_text.domain

import kotlinx.coroutines.flow.StateFlow
import voice_to_text.domain.model.VoiceToTextParserState

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class VoiceToTextParser {
    val state: StateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String, errorMsg: String)
    fun stopListening()
    fun cancel()
    fun reset()
}