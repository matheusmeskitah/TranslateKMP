package translate.di

import core.domain.text_to_speech.TextToSpeechKMP
import org.kodein.di.DI
import org.kodein.di.bindSingleton

actual fun DI.Builder.platformBind() {
    bindSingleton<TextToSpeechKMP> {
        TextToSpeechKMP()
    }
}