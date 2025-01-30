package com.semin.memo.domain.folder

import com.semin.memo.data.database.model.FolderEntity
import kotlinx.datetime.Clock

data class Folder(
    val primaryKey: Long = 0L,
    val category: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isDefault: Boolean,
) {
    companion object {
        val default = Folder(category = "", createdAt = Clock.System.now().toEpochMilliseconds(), updatedAt = Clock.System.now().toEpochMilliseconds(), isDefault = false)
    }

    fun toEntity() = FolderEntity(
        primaryKey = primaryKey,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDefault = isDefault
    )
}
