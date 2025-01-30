package com.semin.memo.presentation.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semin.memo.domain.folder.Folder
import com.semin.memo.domain.folder.FolderRepository
import com.semin.memo.utils.Logs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {

    val folders =
        folderRepository.getAllFolder().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun upsertFolder(folder: Folder) {
        viewModelScope.launch {
            Logs.e("createdAt::${folder.createdAt}\nupdatedAt::${folder.updatedAt}\n")
            Logs.e("isSame::${folder.createdAt == folder.updatedAt}")
            folderRepository.upsertFolder(folder)
        }
    }

    fun deleteFolder(folder: Folder) {
        viewModelScope.launch {
            folderRepository.deleteFolder(folder)
            try {
                folderRepository.upsertLastFolderPrimaryKey(folders.value.first().primaryKey)
            } catch (e: Exception) {
                // todo check UX
                // is minimal folder count 1??
                // or no folder state is available?
            }
        }
    }
}