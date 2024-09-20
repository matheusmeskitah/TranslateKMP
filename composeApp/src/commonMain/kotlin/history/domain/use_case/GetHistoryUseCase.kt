package history.domain.use_case

import history.domain.local.HistoryDAO
import history.presentation.components.UiHistoryItem
import kotlinx.coroutines.flow.map
import translate.data.mapper.toUiHistoryItem
import translate.domain.model.TranslateException

class GetHistoryUseCase(
    private val dao: HistoryDAO
) {
    operator fun invoke(): Result<List<UiHistoryItem>> {
        return try {
//            var history: List<UiHistoryItem> = emptyList()
//
//            dao.getHistory().collect { result ->
//                history = result.map { item -> item.toUiHistoryItem() }
//            }

            Result.success(
                dao.getHistory().map { it.toUiHistoryItem() }
            )
        } catch (e: TranslateException) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}