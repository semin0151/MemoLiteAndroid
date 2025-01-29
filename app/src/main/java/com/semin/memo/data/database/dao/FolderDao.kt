package com.semin.memo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.semin.memo.data.database.DatabaseTable
import com.semin.memo.data.database.model.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query(
        value = """
            SELECT * FROM ${DatabaseTable.FOLDER} ORDER BY updatedAt DESC
        """
    )
    fun getAllFolder(): Flow<List<FolderEntity>>

    @Upsert
    suspend fun upsertFolder(memoEntity: FolderEntity)

    @Delete
    suspend fun deleteFolder(memoEntity: FolderEntity)

    @Query(
        value = """
            DELETE FROM ${DatabaseTable.FOLDER}
        """
    )
    suspend fun deleteAll()
}