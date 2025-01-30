package com.semin.memo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.semin.memo.data.datastore.folderPrefDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @FolderPrefDataStore
    @Provides
    @Singleton
    fun provideFolderPrefDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.folderPrefDataStore
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FolderPrefDataStore