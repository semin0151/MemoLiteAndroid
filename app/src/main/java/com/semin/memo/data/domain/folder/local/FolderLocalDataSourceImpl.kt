package com.semin.memo.data.domain.folder.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.semin.memo.data.database.dao.FolderDao
import com.semin.memo.data.database.dao.MemoDao
import com.semin.memo.data.database.model.FolderEntity
import com.semin.memo.data.datastore.PrefKey
import com.semin.memo.di.FolderPrefDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FolderLocalDataSourceImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val memoDao: MemoDao,
    @FolderPrefDataStore private val prefDataStore: DataStore<Preferences>
) : FolderLocalDataSource {

    override fun getAllFolder(): Flow<List<FolderEntity>> {
        return folderDao.getAllFolder()
    }

    override suspend fun upsertFolder(folderEntity: FolderEntity) {
        folderDao.upsertFolder(folderEntity)
    }

    override suspend fun updateFolder(folderEntity: FolderEntity) {
        folderDao.updateFolder(folderEntity)
    }

    override suspend fun deleteFolder(folderEntity: FolderEntity) {
        folderDao.deleteFolder(folderEntity)
        memoDao.deleteMemoByFolderPrimaryKey(folderEntity.primaryKey)
    }

    override suspend fun deleteAll() {
        folderDao.deleteAll()
    }

    override fun getLastFolder(): Flow<FolderEntity> {
        return prefDataStore.data.map { pref ->
            val primaryKey = pref[PrefKey.Folder.LAST_FOLDER] ?: 0
            folderDao.getFolder(primaryKey)
        }
    }

    override suspend fun upsertLastFolderPrimaryKey(primaryKey: Long) {
        prefDataStore.edit { pref ->
            pref[PrefKey.Folder.LAST_FOLDER] = primaryKey
        }
    }
}