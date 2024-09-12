package translate.domain.repository

import core.domain.language.Language

interface TranslateRepository {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}