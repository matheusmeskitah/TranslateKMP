package core.domain.text_to_speech

import core.domain.language.Language
import platform.AVFAudio.AVSpeechSynthesisVoice.Companion.voiceWithLanguage
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

actual class TextToSpeechKMP {

    private val synthesizer = AVSpeechSynthesizer()
    private val utterance = AVSpeechUtterance()

    actual fun language(language: Language) {
        utterance.voice = voiceWithLanguage(language.langCode)
        utterance.volume = 1F
    }

    actual fun speak(msg: String) {
        synthesizer.speakUtterance(utterance)
    }

    actual fun stop() {
    }
}