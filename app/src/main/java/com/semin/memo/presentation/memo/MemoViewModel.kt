package com.semin.memo.presentation.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semin.memo.domain.folder.FolderRepository
import com.semin.memo.domain.memo.Memo
import com.semin.memo.domain.memo.MemoRepository
import com.semin.memo.utils.Logs
import com.semin.memo.utils.formattedDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoRepository: MemoRepository,
    private val folderRepository: FolderRepository,
) : ViewModel() {
    private val currentTime get() = Clock.System.now().toEpochMilliseconds()

    private val lastPrimaryKey = folderRepository.getLastFolder().map { it.primaryKey }.stateIn(viewModelScope, SharingStarted.Eagerly, 0L)
    val category = folderRepository.getLastFolder().map { it.category }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    private val currentMemoId = MutableStateFlow(0L)

    val folderList =
        folderRepository.getAllFolder().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val memoList: StateFlow<List<Memo>> = memoRepository
        .getAllMemo()
        .combine(lastPrimaryKey) { memoList, primaryKey ->
            Logs.e("combine::\nmemoList::$memoList\ncategory::$primaryKey")
            memoList.filter { it.folderPrimaryKey == primaryKey }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val currentMemo: StateFlow<Memo> = currentMemoId.flatMapConcat {
        if (it != 0L) {
            memoRepository.getMemo(it)
        } else {
            flow { emit(Memo.default) }
        }.catch {

        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Memo.default)

    fun setCurrentMemoId(memoId: Long) {
        currentMemoId.value = memoId
    }

    // todo add folderPrimaryKey
    fun upsertMemo(memoId: Long, title: String, content: String) {
        Logs.e("currentTime::$currentTime")
        Logs.e("currentTime.format::${currentTime.formattedDateTime}")
        viewModelScope.launch {
            if (memoId == 0L) {
                memoRepository.upsertMemo(
                    Memo.default.copy(
                        folderPrimaryKey = lastPrimaryKey.value,
                        category = category.value,
                        title = title,
                        content = content,
                        createdAt = currentTime,
                        updatedAt = currentTime
                    )
                )
            } else {
                memoRepository.getMemo(memoId = memoId).collect {
                    memoRepository.upsertMemo(
                        it.copy(
                            title = title,
                            content = content,
                            updatedAt = currentTime
                        )
                    )
                }
            }
        }
    }

    fun deleteMemo() {
        viewModelScope.launch {
            memoRepository.deleteMemo(currentMemo.value)
        }
    }

    fun upsertLastFolderPrimaryKey(primaryKey: Long) {
        viewModelScope.launch {
            folderRepository.upsertLastFolderPrimaryKey(primaryKey)
        }
    }
}