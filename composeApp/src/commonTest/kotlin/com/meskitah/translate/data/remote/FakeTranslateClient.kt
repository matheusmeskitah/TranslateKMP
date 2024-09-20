package com.meskitah.translate.data.remote

import core.domain.language.Language
import translate.domain.repository.TranslateRepository

class FakeTranslateClient : TranslateRepository {

    var translatedText = "test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}