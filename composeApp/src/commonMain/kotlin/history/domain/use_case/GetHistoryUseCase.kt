package history.domain.use_case

import history.domain.local.HistoryDAO
import history.presentation.components.UiHistoryItem
import translate.data.mapper.toUiHistoryItem
import translate.domain.model.TranslateException

class GetHistoryUseCase(
    private val dao: HistoryDAO
) {
    operator fun invoke(): Result<List<UiHistoryItem>> {
        return try {
            Result.success(
                dao.getHistory().map { it.toUiHistoryItem() }
            )
        } catch (e: TranslateException) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}