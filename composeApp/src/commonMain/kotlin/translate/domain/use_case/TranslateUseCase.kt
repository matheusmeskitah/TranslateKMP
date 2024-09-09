package translate.domain.use_case

import core.domain.language.Language
import translate.domain.history.HistoryDataSource
import translate.domain.history.HistoryItem
import translate.domain.translate.TranslateClient
import translate.domain.translate.TranslateException

class TranslateUseCase(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {
    suspend operator fun invoke(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Result<String> {
        return try {
            val translatedText = client.translate(fromLanguage, fromText, toLanguage)

            historyDataSource.insertHistoryItem(
                HistoryItem(
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText,
                )
            )

            Result.success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}