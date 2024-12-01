package com.semin.memo.presentation.util

class MemoSaveSuccessException(override val message: String = "Memo Saved!") :
    SnackBarException(message) {
}