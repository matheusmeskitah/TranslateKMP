package di

import translate.domain.TextToSpeech
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import voice_to_text.domain.VoiceToTextParser

actual fun DI.Builder.platformBind() {
    bindSingleton<TextToSpeech> {
        TextToSpeech()
    }

    bindSingleton<VoiceToTextParser> {
        VoiceToTextParser()
    }
}