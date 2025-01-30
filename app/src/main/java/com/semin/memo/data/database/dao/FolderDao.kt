package com.semin.memo.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
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

    @Query(
        value = """
            SELECT * FROM ${DatabaseTable.FOLDER} WHERE primaryKey == :primaryKey 
        """
    )
    suspend fun getFolder(primaryKey: Long): FolderEntity

    @Upsert
    suspend fun upsertFolder(folderEntity: FolderEntity)

    @Update
    suspend fun updateFolder(folderEntity: FolderEntity)

    @Delete
    suspend fun deleteFolder(folderEntity: FolderEntity)

    @Query(
        value = """
            DELETE FROM ${DatabaseTable.FOLDER}
        """
    )
    suspend fun deleteAll()
}