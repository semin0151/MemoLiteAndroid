package com.semin.memo.data.domain.memo.local

import com.semin.memo.data.database.model.MemoEntity
import kotlinx.coroutines.flow.Flow

interface MemoLocalDataSource {
    fun getAllMemo(): Flow<List<MemoEntity>>

    fun getMemo(memoId: Long): Flow<MemoEntity>

    suspend fun upsertMemo(memoEntity: MemoEntity)

    suspend fun deleteMemo(memoEntity: MemoEntity)
}