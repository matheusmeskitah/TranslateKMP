package translate.domain

import core.domain.language.Language

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TextToSpeech {
    fun language(language: Language)
    fun speak(msg: String)
    fun stop()
}