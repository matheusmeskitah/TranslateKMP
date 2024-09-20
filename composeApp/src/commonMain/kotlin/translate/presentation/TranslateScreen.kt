package translate.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import history.presentation.components.TranslateHistoryItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import translate.domain.model.TranslateError
import translate.presentation.components.LanguageDropDown
import translate.presentation.components.SwapLanguagesButton
import translate.presentation.components.TranslateTextField
import translate.presentation.components.rememberTextToSpeech
import translatekmp.composeapp.generated.resources.Res
import translatekmp.composeapp.generated.resources.client_error
import translatekmp.composeapp.generated.resources.copied_to_clipboard
import translatekmp.composeapp.generated.resources.error_service_unavailable
import translatekmp.composeapp.generated.resources.history
import translatekmp.composeapp.generated.resources.record_audio
import translatekmp.composeapp.generated.resources.server_error
import translatekmp.composeapp.generated.resources.unknown_error

@Composable
fun TranslateScreen(state: TranslateState, onEvent: (TranslateEvent) -> Unit) {
    val snackbar = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val errorMessage = when (state.error) {
        TranslateError.SERVICE_UNAVAILABLE -> stringResource(Res.string.error_service_unavailable)
        TranslateError.CLIENT_ERROR -> stringResource(Res.string.client_error)
        TranslateError.SERVER_ERROR -> stringResource(Res.string.server_error)
        TranslateError.UNKNOWN_ERROR -> stringResource(Res.string.unknown_error)
        else -> null
    }

    LaunchedEffect(key1 = state.error) {
        errorMessage?.let {
            snackbar
                .showSnackbar(message = it, withDismissAction = true)
                .run {
                    when (this) {
                        SnackbarResult.Dismissed -> onEvent(TranslateEvent.OnErrorSeen)
                        SnackbarResult.ActionPerformed -> Unit
                    }
                }

        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TranslateEvent.RecordAudio) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = stringResource(Res.string.record_audio)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LanguageDropDown(
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseFromLanguage(it)) }
                    )

                    SwapLanguagesButton(onClick = { onEvent(TranslateEvent.SwapLanguages) })

                    LanguageDropDown(
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseToLanguage(it)) }
                    )
                }
            }

            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val text = stringResource(Res.string.copied_to_clipboard)
                val tts = rememberTextToSpeech()

                TranslateTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = { onEvent(TranslateEvent.ChangeTranslationText(it)) },
                    onCopyClick = {
                        clipboardManager.setText(buildAnnotatedString { append(it) })
                        coroutineScope.launch { snackbar.showSnackbar(text) }
                    },
                    onCloseClick = { onEvent(TranslateEvent.CloseTranslation) },
                    onSpeakerClick = {
                        tts.create {
                            if (it) {
                                tts.language(state.toLanguage.language)
                                tts.speak(state.toText ?: "")
                            }
                        }
                    },
                    onTextFieldClick = { onEvent(TranslateEvent.EditTranslation) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                if (state.history.isNotEmpty()) {
                    Text(
                        text = stringResource(Res.string.history),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                if (state.isLoadingHistory) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(64.dp))
                        CircularProgressIndicator()
                    }
                }
            }

            items(state.history) { item ->
                TranslateHistoryItem(
                    item = item,
                    onClick = {
                        onEvent(TranslateEvent.SelectHistoryItem(item))
                    },
                )
            }
        }
    }
}