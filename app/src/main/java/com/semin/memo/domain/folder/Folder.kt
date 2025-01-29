package com.semin.memo.domain.folder

import com.semin.memo.data.database.model.FolderEntity

data class Folder(
    val primaryKey: Long = 0L,
    val category: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    fun toEntity() = FolderEntity(
        primaryKey = primaryKey,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
