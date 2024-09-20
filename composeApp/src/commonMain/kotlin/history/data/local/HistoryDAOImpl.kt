package history.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import history.domain.local.HistoryDAO
import history.domain.entity.HistoryItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi

class HistoryDAOImpl : HistoryDAO {

    companion object {
        const val HISTORY_ITEM_COUNT = "history_item_count"
        const val HISTORY_ITEM = "history_item"

        private val settings: Settings = Settings()
        private val itemCount: Int
            get() {
                return settings.getInt(HISTORY_ITEM_COUNT, 0)
            }
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun getHistory(): Flow<List<HistoryItemEntity>> {
        return flow {
            if (itemCount == 0) emit(emptyList())

            val history = mutableListOf<HistoryItemEntity>()
            for (i in 1..itemCount) {
                val item = settings.decodeValueOrNull(HistoryItemEntity.serializer(), HISTORY_ITEM + i)
                item?.let { history.add(it) }
            }
            emit(history.reversed())
        }
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override suspend fun insertHistoryItem(item: HistoryItemEntity): Boolean {
        return try {
            settings.putInt(HISTORY_ITEM_COUNT, itemCount + 1)

            settings.encodeValue(HistoryItemEntity.serializer(), HISTORY_ITEM + itemCount, item)

            true
        } catch (e: Exception) {
            false
        }
    }
}