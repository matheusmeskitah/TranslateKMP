package translate.di

import core.domain.translate.TextToSpeechKMP
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

actual fun DI.Builder.platformBind() {
    bindSingleton<TextToSpeechKMP> {
        TextToSpeechKMP(context = instance())
    }
}