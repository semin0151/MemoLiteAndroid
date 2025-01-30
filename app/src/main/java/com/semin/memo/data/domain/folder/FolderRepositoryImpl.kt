package com.semin.memo.data.domain.folder

import com.semin.memo.data.domain.folder.local.FolderLocalDataSource
import com.semin.memo.domain.folder.Folder
import com.semin.memo.domain.folder.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val localDataSource: FolderLocalDataSource
) : FolderRepository {
    override fun getAllFolder(): Flow<List<Folder>> {
        return localDataSource.getAllFolder().map { list ->
            list.map { it.toModel() }
        }
    }

    override suspend fun upsertFolder(folder: Folder) {
        localDataSource.upsertFolder(folder.toEntity())
    }

    override suspend fun updateFolder(folder: Folder) {
        localDataSource.updateFolder(folder.toEntity())
    }

    override suspend fun deleteFolder(folder: Folder) {
        localDataSource.deleteFolder(folder.toEntity())
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }

    override fun getLastFolder(): Flow<Folder> {
        return localDataSource.getLastFolder().map { it.toModel() }
    }

    override suspend fun upsertLastFolderPrimaryKey(primaryKey: Long) {
        localDataSource.upsertLastFolderPrimaryKey(primaryKey)
    }
}