package translate.data.mapper

import core.presentation.UiLanguage
import history.domain.HistoryItem
import history.presentation.components.UiHistoryItem

fun HistoryItem.toUiHistoryItem(): UiHistoryItem {
    return UiHistoryItem(
        fromText = this.fromText,
        toText = this.toText,
        fromLanguage = UiLanguage.byCode(this.fromLanguageCode),
        toLanguage = UiLanguage.byCode(this.toLanguageCode)
    )
}