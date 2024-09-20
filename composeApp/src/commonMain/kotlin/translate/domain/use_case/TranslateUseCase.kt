package translate.domain.use_case

import core.domain.language.Language
import translate.domain.model.TranslateException
import translate.domain.repository.TranslateRepository

class TranslateUseCase(
    private val repository: TranslateRepository
) {
    suspend operator fun invoke(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Result<String> {
        return try {
            val translatedText = repository.translate(fromLanguage, fromText, toLanguage)

            Result.success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}