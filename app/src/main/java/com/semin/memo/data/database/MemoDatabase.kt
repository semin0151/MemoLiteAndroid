package com.semin.memo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.semin.memo.data.database.dao.MemoDao
import com.semin.memo.data.database.model.MemoEntity

@Database(
    entities = [MemoEntity::class],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}