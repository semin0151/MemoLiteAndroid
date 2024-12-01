package com.semin.memo.di

import android.content.Context
import androidx.room.Room
import com.semin.memo.data.database.MemoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): MemoDatabase = Room.databaseBuilder(
        context = context,
        klass = MemoDatabase::class.java,
        "semin-memo-database"
    ).build()
}