package com.semin.memo.domain.memo

import com.semin.memo.data.database.model.MemoEntity

data class Memo(
    val primaryKey: Long = 0L,
    val category: String = Category.Default.name,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        val default = Memo(
            title = "",
            content = "",
            createdAt = 0L,
            updatedAt = 0L
        )
    }

    fun toEntity() = MemoEntity(
        primaryKey = primaryKey,
        category = category,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

enum class Category {
    Default
}