package translate.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import core.domain.util.CommonFlow
import core.domain.util.toCommonFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import translate.domain.history.HistoryDataSource
import translate.domain.history.HistoryItem

class HistoryDAO : HistoryDataSource {

    companion object {
        const val HISTORY_ITEM_COUNT = "history_item_count"
        const val HISTORY_ITEM = "history_item"

        private val settings: Settings = Settings()
        private val itemCount: Int
            get() {
                return settings.getInt(HISTORY_ITEM_COUNT, 0)
            }
    }


    @OptIn(ExperimentalSerializationApi::class)
    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return flow<List<HistoryItem>> {
            for (i in 1..itemCount) {
                val item = settings.decodeValueOrNull(HistoryItem.serializer(), HISTORY_ITEM + i)
                item?.let { emit(listOf()) }
            }
        }.toCommonFlow()
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override suspend fun insertHistoryItem(item: HistoryItem) {
        settings.putInt(HISTORY_ITEM_COUNT, itemCount + 1)

        settings.encodeValue(HistoryItem.serializer(), HISTORY_ITEM + itemCount, item)
    }
}