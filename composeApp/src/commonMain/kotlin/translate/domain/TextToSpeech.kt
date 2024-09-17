package translate.domain

import core.domain.language.Language

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TextToSpeech {
    fun create(isInit: (Boolean) -> Unit)
    fun language(language: Language)
    fun speak(msg: String)
    fun stop()
}