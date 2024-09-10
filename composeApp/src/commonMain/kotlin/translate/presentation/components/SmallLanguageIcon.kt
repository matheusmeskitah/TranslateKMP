package translate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.UiLanguage
import org.jetbrains.compose.resources.painterResource

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(language.drawableRes),
        contentDescription = language.language.langName,
        modifier = modifier.size(25.dp)
    )
}