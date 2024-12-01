package com.semin.memo.data.domain.memo

import com.semin.memo.data.domain.memo.local.MemoLocalDataSource
import com.semin.memo.domain.memo.Memo
import com.semin.memo.domain.memo.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val localDataSource: MemoLocalDataSource
) : MemoRepository {
    override fun getAllMemo(): Flow<List<Memo>> = localDataSource.getAllMemo().map { lists ->
        lists.map { it.toModel() }
    }

    override fun getMemo(memoId: Long): Flow<Memo> = localDataSource.getMemo(memoId).map {
        it.toModel()
    }

    override suspend fun upsertMemo(memo: Memo) {
        localDataSource.upsertMemo(memo.toEntity())
    }

    override suspend fun deleteMemo(memo: Memo) {
        localDataSource.deleteMemo(memo.toEntity())
    }

}