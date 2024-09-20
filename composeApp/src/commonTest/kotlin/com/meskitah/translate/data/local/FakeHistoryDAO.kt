package com.meskitah.translate.data.local

import history.domain.local.HistoryDAO
import history.domain.entity.HistoryItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeHistoryDAO : HistoryDAO {

    private val _data = MutableStateFlow<List<HistoryItemEntity>>(emptyList())

    override fun getHistory(): List<HistoryItemEntity> {
        return _data.value
    }

    override fun insertHistoryItem(item: HistoryItemEntity): Boolean {
        _data.value += item
        return true
    }
}