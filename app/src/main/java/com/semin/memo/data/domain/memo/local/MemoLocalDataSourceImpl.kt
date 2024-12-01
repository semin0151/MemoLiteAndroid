package com.semin.memo.data.domain.memo.local

import com.semin.memo.data.database.dao.MemoDao
import com.semin.memo.data.database.model.MemoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val dao: MemoDao
) : MemoLocalDataSource {
    override fun getAllMemo(): Flow<List<MemoEntity>> = dao.getAllMemo()

    override fun getMemo(memoId: Long): Flow<MemoEntity> = dao.getMemo(memoId)

    override suspend fun upsertMemo(memoEntity: MemoEntity) {
        dao.upsertMemo(memoEntity = memoEntity)
    }

    override suspend fun deleteMemo(memoEntity: MemoEntity) {
        dao.deleteMemo(memoEntity = memoEntity)
    }
}