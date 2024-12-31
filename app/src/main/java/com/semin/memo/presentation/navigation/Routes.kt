package com.semin.memo.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Memo : Route()

    @Serializable
    data class MemoUpsert(
        val memoId: Long,
        val memoTitle: String,
        val memoContent: String
    ) : Route()

    @Serializable
    data class MemoDetail(val memoId: Long) : Route()
}