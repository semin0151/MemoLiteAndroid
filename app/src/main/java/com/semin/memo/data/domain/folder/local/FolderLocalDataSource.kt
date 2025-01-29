package com.semin.memo.data.domain.folder.local

import com.semin.memo.data.database.model.FolderEntity
import kotlinx.coroutines.flow.Flow

interface FolderLocalDataSource {
    fun getAllFolder(): Flow<List<FolderEntity>>

    suspend fun upsertFolder(folderEntity: FolderEntity)

    suspend fun deleteFolder(folderEntity: FolderEntity)

    suspend fun deleteAll()
}