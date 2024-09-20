package history.domain.local

import history.domain.entity.HistoryItemEntity
import kotlinx.coroutines.flow.Flow

interface HistoryDAO {
    fun getHistory(): List<HistoryItemEntity>
    fun insertHistoryItem(item: HistoryItemEntity): Boolean
}