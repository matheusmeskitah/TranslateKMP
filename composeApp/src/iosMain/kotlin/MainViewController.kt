import androidx.compose.ui.window.ComposeUIViewController
import org.kodein.di.compose.withDI
import di.mainModule

fun MainViewController() = ComposeUIViewController {
    withDI(mainModule) {
        App()
    }
}