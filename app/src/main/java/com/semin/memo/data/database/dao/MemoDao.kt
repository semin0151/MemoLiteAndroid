package com.semin.memo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.semin.memo.data.database.DatabaseTable
import com.semin.memo.data.database.model.MemoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query(
        value = """
            SELECT * FROM ${DatabaseTable.MEMO}
        """
    )
    fun getAllMemo(): Flow<List<MemoEntity>>

    @Query(
        value = """
            SELECT * FROM ${DatabaseTable.MEMO} WHERE primaryKey = :memoId
        """
    )
    fun getMemo(memoId: Long): Flow<MemoEntity>

    @Upsert
    suspend fun upsertMemo(memoEntity: MemoEntity)

    @Delete
    suspend fun deleteMemo(memoEntity: MemoEntity)
}