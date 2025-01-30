package com.semin.memo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.semin.memo.data.database.DatabaseTable
import com.semin.memo.domain.folder.Folder

@Entity(tableName = DatabaseTable.FOLDER)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val primaryKey: Long,
    val category: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isDefault: Boolean
) {
    fun toModel() = Folder(
        primaryKey = primaryKey,
        category = category,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDefault = isDefault
    )
}