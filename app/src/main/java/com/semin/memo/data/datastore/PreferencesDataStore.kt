package com.semin.memo.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.folderPrefDataStore: DataStore<Preferences> by preferencesDataStore(PrefFileName.FOLDER)

object PrefFileName {
    const val FOLDER = "folder"
}

sealed interface PrefKey {
    data object Folder {
        val LAST_FOLDER = longPreferencesKey("last_folder")
    }
}