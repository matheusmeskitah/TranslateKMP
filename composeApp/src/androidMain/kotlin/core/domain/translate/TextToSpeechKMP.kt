package core.domain.translate

import android.content.Context
import android.speech.tts.TextToSpeech
import core.domain.language.Language
import java.util.Locale

actual class TextToSpeechKMP(
    private val context: Context
) {
    private val tts: TextToSpeech = TextToSpeech(context, null)

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
        tts.stop()
        tts.shutdown()
    }
}