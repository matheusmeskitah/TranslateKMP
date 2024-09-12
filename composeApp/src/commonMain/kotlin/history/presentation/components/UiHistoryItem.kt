package history.presentation.components

import core.presentation.UiLanguage

data class UiHistoryItem(
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)