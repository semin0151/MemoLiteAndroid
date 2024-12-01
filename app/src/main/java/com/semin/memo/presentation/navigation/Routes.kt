package com.semin.memo.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Route() {
    @Serializable
    data object Memo : Route()

    @Serializable
    data class MemoUpsert(
        val memoId: Long,
        val memoTitle: String,
        val memoContent: String
    ) : Route() {
        companion object{
            val dataClassRoute: String? get() = this.javaClass.canonicalName?.replace(".Companion", "")
        }
    }

    @Serializable
    data class MemoDetail(val memoId: Long) : Route() {
        companion object{
            val dataClassRoute: String? get() = this.javaClass.canonicalName?.replace(".Companion", "")
        }
    }

    val route: String? get() = this.javaClass.canonicalName
}