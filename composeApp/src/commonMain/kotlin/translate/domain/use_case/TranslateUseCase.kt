package translate.domain.use_case

import core.domain.language.Language
import history.domain.HistoryDataSource
import history.domain.HistoryItem
import translate.domain.repository.TranslateRepository
import translate.domain.model.TranslateException

class TranslateUseCase(
    private val client: TranslateRepository,
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