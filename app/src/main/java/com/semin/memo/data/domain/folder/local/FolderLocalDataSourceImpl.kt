package com.semin.memo.data.domain.folder.local

import com.semin.memo.data.database.dao.FolderDao
import com.semin.memo.data.database.model.FolderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderLocalDataSourceImpl @Inject constructor(
    private val dao: FolderDao
): FolderLocalDataSource {

    override fun getAllFolder(): Flow<List<FolderEntity>> {
        return dao.getAllFolder()
    }

    override suspend fun upsertFolder(folderEntity: FolderEntity) {
        dao.upsertFolder(folderEntity)
    }

    override suspend fun deleteFolder(folderEntity: FolderEntity) {
        dao.deleteFolder(folderEntity)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}