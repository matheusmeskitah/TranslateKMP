package di

import core.permissions.OpenSettingsHelper
import core.permissions.OpenSettingsKMP
import core.permissions.Permissions
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import translate.domain.TextToSpeech
import voice_to_text.domain.VoiceToTextParser

actual fun DI.Builder.platformBind() {
    bindSingleton<TextToSpeech> {
        TextToSpeech()
    }

    bindSingleton<VoiceToTextParser> {
        VoiceToTextParser()
    }

    bindSingleton<OpenSettingsHelper> {
        OpenSettingsKMP()
    }

    bindSingleton<Permissions> {
        Permissions()
    }
}