package core.domain.translate

import core.domain.language.Language

expect class TextToSpeechKMP {
    fun language(language: Language)
    fun speak(msg: String)
    fun stop()
}