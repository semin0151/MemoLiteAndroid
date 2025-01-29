package com.semin.memo.presentation.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semin.memo.domain.folder.Folder
import com.semin.memo.domain.folder.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {

    val folders =
        folderRepository.getAllFolder().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val currentTime get() = Clock.System.now().toEpochMilliseconds()

    fun upsertFolder(name: String) {
        viewModelScope.launch {
            folderRepository.upsertFolder(Folder(category = name, createdAt = currentTime, updatedAt = currentTime))
        }
    }

    fun deleteFolder(folder: Folder) {
        viewModelScope.launch {
            folderRepository.deleteFolder(folder)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            folderRepository.deleteAll()
        }
    }
}