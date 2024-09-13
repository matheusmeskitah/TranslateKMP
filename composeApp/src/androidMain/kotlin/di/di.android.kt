package di

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import core.permissions.OpenSettingsHelper
import core.permissions.OpenSettingsKMP
import core.permissions.Permissions
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import translate.domain.TextToSpeech
import voice_to_text.domain.VoiceToTextParser

actual fun DI.Builder.platformBind() {
    bindSingleton<TextToSpeech> {
        TextToSpeech(context = instance())
    }

    bindSingleton<VoiceToTextParser> {
        VoiceToTextParser(app = instance())
    }

    bindSingleton<OpenSettingsHelper> {
        val context: Context = instance()
        OpenSettingsKMP(settingsOpener = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setData(Uri.fromParts("package", context.packageName, null))
            context.startActivity(intent)
            true
        })
    }

    bindSingleton<Permissions> {
        Permissions()
    }
}