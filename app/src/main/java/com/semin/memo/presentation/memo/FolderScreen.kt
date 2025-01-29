package com.semin.memo.presentation.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FolderUpsertScreen(
    modifier: Modifier = Modifier,
    viewModel: FolderViewModel = hiltViewModel()
) {
    val folders = viewModel.folders.collectAsStateWithLifecycle()
    val folderText = remember { derivedStateOf { folders.value.toString() } }
    // todo upsert folder screen
    Column {

        Text(modifier = Modifier.fillMaxWidth(), text = folderText.value)

        Row {
            Button(onClick = {
                viewModel.upsertFolder("seminzzang")
            }) {
                Text("ADD")
            }

            Button(onClick = {
                viewModel.deleteAll()
            }) {
                Text("DELETE")
            }
        }
    }
}