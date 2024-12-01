package com.semin.memo.presentation.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semin.memo.domain.memo.Category
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {
    private val currentTime get() = Clock.System.now().toEpochMilliseconds()

    private val category = MutableStateFlow(Category.Default.name)

    private val currentMemoId = MutableStateFlow(0L)

    val memoList: StateFlow<List<Memo>> = memoRepository
        .getAllMemo()
        .combine(category) { memoList, category ->
            memoList.filter { it.category == category }
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

    fun upsertMemo(memoId: Long, title: String, content: String) {
        Logs.e("currentTime::$currentTime")
        Logs.e("currentTime.format::${currentTime.formattedDateTime}")
        viewModelScope.launch {
            if (memoId == 0L) {
                memoRepository.upsertMemo(Memo.default.copy(title = title, content = content, createdAt = currentTime, updatedAt = currentTime))
            } else {
                memoRepository.getMemo(memoId = memoId).collect {
                    memoRepository.upsertMemo(it.copy(title = title, content = content, updatedAt = currentTime))
                }
            }
        }
    }

    fun deleteMemo() {
        viewModelScope.launch {
            memoRepository.deleteMemo(currentMemo.value)
        }
    }
}