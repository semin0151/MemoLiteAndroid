package com.semin.memo.presentation.util

class NotReadyException(override val message: String = "아직 준비중입니다!") : SnackBarException(message) {
}