package com.semin.memo.di

import com.semin.memo.data.domain.folder.FolderRepositoryImpl
import com.semin.memo.data.domain.folder.local.FolderLocalDataSource
import com.semin.memo.data.domain.folder.local.FolderLocalDataSourceImpl
import com.semin.memo.domain.folder.FolderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FolderModule {

    @Binds
    abstract fun provideFolderLocalDataSource(
        folderLocalDataSource: FolderLocalDataSourceImpl
    ): FolderLocalDataSource

    @Binds
    abstract fun provideFolderRepository(
        folderRepository: FolderRepositoryImpl
    ): FolderRepository
}