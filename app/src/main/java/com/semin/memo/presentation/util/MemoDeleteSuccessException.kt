package com.semin.memo.presentation.util

class MemoDeleteSuccessException(override val message: String = "Memo Deleted!") :
    SnackBarException(message) {
}