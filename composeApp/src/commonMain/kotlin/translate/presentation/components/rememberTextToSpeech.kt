package translate.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import core.domain.text_to_speech.TextToSpeechKMP
import org.kodein.di.compose.localDI
import org.kodein.di.instance

@Composable
fun rememberTextToSpeech(): TextToSpeechKMP = with(localDI()) {
    val tts by instance<TextToSpeechKMP>()
    DisposableEffect(key1 = tts) {
        onDispose {
            tts.stop()
        }
    }

    return tts
}