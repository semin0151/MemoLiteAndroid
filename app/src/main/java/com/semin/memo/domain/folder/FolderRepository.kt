package com.semin.memo.domain.folder

import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    fun getAllFolder(): Flow<List<Folder>>

    suspend fun upsertFolder(folder: Folder)

    suspend fun deleteFolder(folder: Folder)

    suspend fun deleteAll()
}