package translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import history.domain.use_case.GetHistoryUseCase
import history.domain.use_case.SaveLocallyUseCase
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import translate.domain.model.TranslateException
import translate.domain.use_case.TranslateUseCase

class TranslateViewModel(
    private val translateUseCase: TranslateUseCase,
    private val saveLocallyUseCase: SaveLocallyUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TranslateState())
    val state = _state
        .onStart {
            loadHistory()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = TranslateState()
        )

    private var translateJob: Job? = null

    fun onEvent(event: TranslateEvent) {
        when (event) {
            is TranslateEvent.ChangeTranslationText -> _state.update { it.copy(fromText = event.text) }

            is TranslateEvent.ChooseFromLanguage -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = false,
                        fromLanguage = event.language
                    )
                }
            }

            is TranslateEvent.ChooseToLanguage -> {
                val newState = _state.updateAndGet {
                    it.copy(
                        isChoosingToLanguage = false,
                        toLanguage = event.language
                    )
                }
                translate(newState)
            }

            TranslateEvent.CloseTranslation -> {
                _state.update {
                    it.copy(
                        isTranslating = false,
                        fromText = "",
                        toText = null
                    )
                }
            }

            TranslateEvent.EditTranslation -> {
                if (state.value.toText != null) {
                    _state.update {
                        it.copy(
                            toText = null,
                            isTranslating = false
                        )
                    }
                }
            }

            TranslateEvent.OnErrorSeen -> {
                _state.update { it.copy(error = null) }
            }

            TranslateEvent.OpenFromLanguageDropDown -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = true
                    )
                }
            }

            TranslateEvent.OpenToLanguageDropDown -> {
                _state.update {
                    it.copy(
                        isChoosingToLanguage = true
                    )
                }
            }

            is TranslateEvent.SelectHistoryItem -> {
                translateJob?.cancel()
                _state.update {
                    it.copy(
                        fromText = event.item.fromText,
                        toText = event.item.toText,
                        isTranslating = false,
                        fromLanguage = event.item.fromLanguage,
                        toLanguage = event.item.toLanguage
                    )
                }
            }

            TranslateEvent.StopChoosingLanguage -> {
                _state.update {
                    it.copy(
                        isChoosingFromLanguage = false,
                        isChoosingToLanguage = false
                    )
                }
            }

            is TranslateEvent.SubmitVoiceResult -> {
                _state.update {
                    it.copy(
                        fromText = event.result ?: it.fromText,
                        isTranslating = if (event.result != null) false else it.isTranslating,
                        toText = if (event.result != null) null else it.toText
                    )
                }
            }

            TranslateEvent.SwapLanguages -> {
                _state.update {
                    it.copy(
                        fromLanguage = it.toLanguage,
                        toLanguage = it.fromLanguage,
                        fromText = it.toText ?: "",
                        toText = if (it.toText != null) it.fromText else null
                    )
                }
            }

            TranslateEvent.Translate -> translate(state.value)

            else -> Unit
        }
    }

    private fun translate(state: TranslateState) {
        if (state.isTranslating || state.fromText.isBlank()) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isTranslating = true) }

            translateUseCase(
                fromLanguage = state.fromLanguage.language,
                fromText = state.fromText,
                toLanguage = state.toLanguage.language
            ).onSuccess { result ->
                _state.update {
                    it.copy(
                        isTranslating = false,
                        toText = result
                    )
                }

                saveHistoryItem()
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isTranslating = false,
                        error = (error as? TranslateException)?.error
                    )
                }
            }
        }
    }

    private fun saveHistoryItem() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingHistory = true) }

            saveLocallyUseCase(
                fromLanguageCode = state.value.fromLanguage.language.langCode,
                fromText = state.value.fromText,
                toLanguageCode = state.value.toLanguage.language.langCode,
                translatedText = state.value.toText ?: ""
            ).onSuccess {
                loadHistory()
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoadingHistory = false,
                        error = (error as? TranslateException)?.error
                    )
                }
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingHistory = true) }

            getHistoryUseCase()
                .onSuccess { history ->
                    _state.update { it.copy(history = history, isLoadingHistory = false) }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoadingHistory = false,
                            error = (error as? TranslateException)?.error
                        )
                    }
                }
        }
    }
}