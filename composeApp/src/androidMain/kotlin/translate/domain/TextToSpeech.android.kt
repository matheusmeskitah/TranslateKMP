package translate.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import core.domain.language.Language
import java.util.Locale

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TextToSpeech(val context: Context) {
    private lateinit var tts: TextToSpeech

    actual fun create(isInit: (Boolean) -> Unit) {
        tts = TextToSpeech(context) {
            isInit(it == TextToSpeech.SUCCESS)
        }
    }

    actual fun language(language: Language) {
        val locale = when (language) {
            Language.ENGLISH -> Locale.ENGLISH
            Language.CHINESE -> Locale.CHINESE
            Language.FRENCH -> Locale.FRENCH
            Language.GERMAN -> Locale.GERMAN
            Language.ITALIAN -> Locale.ITALIAN
            Language.JAPANESE -> Locale.JAPANESE
            Language.KOREAN -> Locale.KOREAN
            else -> Locale.ENGLISH
        }

        tts.language = locale
    }

    actual fun speak(msg: String) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    actual fun stop() {
        if (this::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }
}