package com.semin.memo.utils

import android.util.Log

object Logs {
    private const val TAG = "seminzzang"

    fun e(message: String) {
        Log.e(TAG, message)
    }
}