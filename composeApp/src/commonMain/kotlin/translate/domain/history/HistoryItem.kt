package translate.domain.history

import kotlinx.serialization.Serializable

@Serializable
data class HistoryItem(
    val id: Long?,
    val fromLanguageCode: String,
    val fromText: String,
    val toLanguageCode: String,
    val toText: String,
)