package com.semin.memo.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semin.memo.domain.folder.Folder
import com.semin.memo.domain.folder.FolderRepository
import com.semin.memo.utils.Logs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {
    private val currentTime get() = Clock.System.now().toEpochMilliseconds()

    val uiState = flow {
        try {
            Logs.e("try")
            val getLastFolder = folderRepository.getLastFolder().first()
            Logs.e("getLastFolder::$getLastFolder")
        } catch (e: Exception) {
            Logs.e("catch")
            val allFolder = folderRepository.getAllFolder().first()

            Logs.e("allFolder::${allFolder}")
            if (allFolder.isEmpty()) {
                folderRepository.upsertFolder(
                    Folder(
                        category = "Default",
                        createdAt = currentTime,
                        updatedAt = currentTime,
                        isDefault = true
                    )
                )
            }

            val lastFolder = folderRepository.getAllFolder().first().first()
            folderRepository.upsertLastFolderPrimaryKey(lastFolder.primaryKey)
            Logs.e("upsertLastFolder::${lastFolder}")
        }

        emit(true)
    }.map {
        MainActivityUiState.Success(it.toString())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, MainActivityUiState.Loading)
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: String) : MainActivityUiState
}