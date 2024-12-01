package com.semin.memo.domain.memo

import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun getAllMemo(): Flow<List<Memo>>

    fun getMemo(memoId: Long): Flow<Memo>

    suspend fun upsertMemo(memo: Memo)

    suspend fun deleteMemo(memo: Memo)
}