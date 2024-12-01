package com.semin.memo.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val uiState = flow {
        emit(true)
    }.map {
        MainActivityUiState.Success(it.toString())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, MainActivityUiState.Loading)
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: String) : MainActivityUiState
}