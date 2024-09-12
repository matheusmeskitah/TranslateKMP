package history.domain

import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun getHistory(): Flow<List<HistoryItem>>
    suspend fun insertHistoryItem(item: HistoryItem)
}