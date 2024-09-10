package translate.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.LightBlue
import core.presentation.UiLanguage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import translatekmp.composeapp.generated.resources.Res
import translatekmp.composeapp.generated.resources.close
import translatekmp.composeapp.generated.resources.open

@Composable
fun LanguageDropDown(
    language: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (UiLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onDismiss
        ) {
            UiLanguage.allLanguages.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = language.language.langName,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    onClick = { onSelectLanguage(language) },
                    leadingIcon = {
                        Image(
                            painter = painterResource(language.drawableRes),
                            contentDescription = language.language.langName,
                            modifier = Modifier.size(36.dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Row(
            modifier = Modifier
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(language.drawableRes),
                contentDescription = language.language.langName,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = language.language.langName,
                style = MaterialTheme.typography.bodyMedium,
                color = LightBlue
            )

            Icon(
                imageVector = if (isOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isOpen)
                    stringResource(Res.string.close)
                else stringResource(Res.string.open),
                tint = LightBlue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}