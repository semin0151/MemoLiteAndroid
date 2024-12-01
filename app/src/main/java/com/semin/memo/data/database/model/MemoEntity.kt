package com.semin.memo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.semin.memo.data.database.DatabaseTable
import com.semin.memo.domain.memo.Memo

@Entity(tableName = DatabaseTable.MEMO)
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) val primaryKey: Long,
    val category: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    fun toModel() = Memo(
        primaryKey = primaryKey,
        category = category,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
