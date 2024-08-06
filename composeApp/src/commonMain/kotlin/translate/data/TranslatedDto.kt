package translate.data

import kotlinx.serialization.Serializable

@Serializable
data class TranslatedDto(
    val translatedText: String
)