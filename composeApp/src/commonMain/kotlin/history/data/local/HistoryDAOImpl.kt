package history.data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import history.domain.entity.HistoryItemEntity
import history.domain.local.HistoryDAO
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
    override fun getHistory(): List<HistoryItemEntity> {
        if (itemCount == 0)
            return emptyList()

        val history = mutableListOf<HistoryItemEntity>()
        for (i in 1..itemCount) {
            val item = settings.decodeValueOrNull(HistoryItemEntity.serializer(), HISTORY_ITEM + i)
            item?.let { history.add(it) }
        }
        return history.reversed()
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun insertHistoryItem(item: HistoryItemEntity): Boolean {
        return try {
            settings.putInt(HISTORY_ITEM_COUNT, itemCount + 1)

            settings.encodeValue(HistoryItemEntity.serializer(), HISTORY_ITEM + itemCount, item)

            true
        } catch (e: Exception) {
            false
        }
    }
}