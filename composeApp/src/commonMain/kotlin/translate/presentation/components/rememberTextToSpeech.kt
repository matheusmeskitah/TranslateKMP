package translate.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import org.kodein.di.compose.localDI
import org.kodein.di.instance
import translate.domain.TextToSpeech

@Composable
fun rememberTextToSpeech(): TextToSpeech = with(localDI()) {
    val tts by instance<TextToSpeech>()
    DisposableEffect(key1 = tts) {
        onDispose {
            tts.stop()
        }
    }

    return remember { tts }
}