package com.semin.memo.di

import com.semin.memo.data.domain.memo.MemoRepositoryImpl
import com.semin.memo.data.domain.memo.local.MemoLocalDataSource
import com.semin.memo.data.domain.memo.local.MemoLocalDataSourceImpl
import com.semin.memo.domain.memo.MemoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MemoModule {

    @Binds
    abstract fun provideMemoLocalDataSource(
        memoLocalDataSource: MemoLocalDataSourceImpl
    ): MemoLocalDataSource

    @Binds
    abstract fun provideMemoRepository(
        memoRepository: MemoRepositoryImpl
    ): MemoRepository
}