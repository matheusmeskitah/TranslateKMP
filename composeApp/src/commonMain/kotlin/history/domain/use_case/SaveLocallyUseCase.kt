package history.domain.use_case

import history.domain.entity.HistoryItemEntity
import history.domain.local.HistoryDAO
import translate.domain.model.TranslateError
import translate.domain.model.TranslateException

class SaveLocallyUseCase(
    private val dao: HistoryDAO
) {
    suspend operator fun invoke(
        fromLanguageCode: String,
        fromText: String,
        toLanguageCode: String,
        translatedText: String
    ): Result<Boolean> {
        return try {
            val isSaved = dao.insertHistoryItem(
                HistoryItemEntity(
                    fromLanguageCode = fromLanguageCode,
                    fromText = fromText,
                    toLanguageCode = toLanguageCode,
                    toText = translatedText,
                )
            )

            if (isSaved)
                Result.success(isSaved)
            else Result.failure(TranslateException(TranslateError.UNKNOWN_ERROR))
        } catch (e: TranslateException) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}