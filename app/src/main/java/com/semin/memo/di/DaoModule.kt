package com.semin.memo.di

import com.semin.memo.data.database.MemoDatabase
import com.semin.memo.data.database.dao.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun bindMemoDao(
        database: MemoDatabase
    ): MemoDao = database.memoDao()
}