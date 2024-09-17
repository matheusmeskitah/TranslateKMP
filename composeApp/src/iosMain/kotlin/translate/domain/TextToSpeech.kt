package translate.domain

import core.domain.language.Language
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TextToSpeech {

    private lateinit var synthesizer: AVSpeechSynthesizer
    private lateinit var utterance: AVSpeechUtterance

    actual fun create(isInit: (Boolean) -> Unit) {
        synthesizer = AVSpeechSynthesizer()
        utterance = AVSpeechUtterance()
        isInit(true)
    }

    actual fun language(language: Language) {
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(language.langCode)
        utterance.volume = 1F
    }

    actual fun speak(msg: String) {
        synthesizer.speakUtterance(utterance)
    }

    actual fun stop() {
    }
}