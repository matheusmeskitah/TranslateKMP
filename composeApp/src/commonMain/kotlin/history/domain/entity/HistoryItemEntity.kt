package history.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class HistoryItemEntity(
    val fromLanguageCode: String,
    val fromText: String,
    val toLanguageCode: String,
    val toText: String,
)