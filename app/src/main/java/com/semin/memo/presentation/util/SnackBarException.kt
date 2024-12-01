package com.semin.memo.presentation.util

import androidx.annotation.StringRes

abstract class SnackBarException(override val message: String) : Exception(message)