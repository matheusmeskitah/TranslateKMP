import androidx.compose.ui.window.ComposeUIViewController
import org.kodein.di.compose.withDI
import translate.di.mainModule

fun MainViewController() = ComposeUIViewController {
    withDI(mainModule) {
        App()
    }
}