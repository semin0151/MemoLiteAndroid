package com.semin.memo.domain.memo

import com.semin.memo.data.database.model.MemoEntity

data class Memo(
    val primaryKey: Long = 0L,
    val folderPrimaryKey: Long,
    val category: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        val default = Memo(
            folderPrimaryKey = 0L,
            category = "",
            title = "",
            content = "",
            createdAt = 0L,
            updatedAt = 0L
        )
    }

    fun toEntity() = MemoEntity(
        primaryKey = primaryKey,
        folderPrimaryKey = folderPrimaryKey,
        category = category,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
