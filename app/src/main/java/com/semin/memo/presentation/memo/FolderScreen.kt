package com.semin.memo.presentation.memo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semin.memo.domain.folder.Folder
import com.semin.memo.utils.Logs
import com.semin.memo.utils.formattedDateTime
import kotlinx.datetime.Clock

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderUpsertScreen(
    modifier: Modifier = Modifier,
    viewModel: FolderViewModel = hiltViewModel()
) {
    val folders = viewModel.folders.collectAsStateWithLifecycle()
    val openDialog: MutableState<Folder?> = remember { mutableStateOf(null) }
    val openDialog2: MutableState<Folder?> = remember { mutableStateOf(null) }
    // todo upsert folder screen
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            LazyColumn {
                items(folders.value) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .combinedClickable(
                                onClick = {
                                    Logs.e("combinedClickable::onClick!!")
                                },
                                onLongClick = {
                                    Logs.e("combinedClickable::onLongClick!!::${it.primaryKey}")
                                    if (it.isDefault.not()) openDialog2.value = it
                                }
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .weight(1F)
                                .align(Alignment.CenterVertically),
                            text = it.category,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 20.sp
                        )

                        Text(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .widthIn(80.dp)
                                .align(Alignment.CenterVertically),
                            text = it.updatedAt.formattedDateTime,
                            textAlign = TextAlign.End,
                            fontSize = 12.sp
                        )
                    }

                    HorizontalDivider()
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp),
            onClick = {
                // todo add folder
                openDialog.value = Folder.default
//                viewModel.upsertFolder("seminzzang${Random.nextInt().absoluteValue.rem(100)}")
            }
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Add,
                contentDescription = "FAB",
            )
        }
    }

    if (openDialog.value != null) {
        CustomDialog(
            folder = openDialog.value,
            onDismissRequest = {
                openDialog.value = null
                openDialog2.value = null
            },
            onOKClick = { folder ->
                openDialog.value = null
                openDialog2.value = null
                viewModel.upsertFolder(folder)
            },
            onCloseClick = {
                openDialog.value = null
                openDialog2.value = null
            }
        )
    }

    if (openDialog2.value != null) {
        CustomDialog2(
            folder = openDialog2.value!!,
            onDismissRequest = {
                openDialog2.value = null
            },
            onModifyClick = { folder ->
                openDialog.value = folder
            },
            onDeleteClick = { folder ->
                viewModel.deleteFolder(folder = folder)
                openDialog2.value = null
            }
        )
    }
}

@Composable
fun CustomDialog(
    folder: Folder?,
    onDismissRequest: () -> Unit,
    onOKClick: (Folder) -> Unit,
    onCloseClick: () -> Unit
) {
    val name =
        if (folder == null) remember { mutableStateOf("") } else remember { mutableStateOf(folder.category) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    value = name.value,
                    onValueChange = { str -> name.value = str },
                    label = { Text("Folder Name") },
                    singleLine = true
                )

                Row(modifier = Modifier.padding(20.dp)) {
                    Button(modifier = Modifier.weight(1F), onClick = onCloseClick) {
                        Text(text = "Close")
                    }
                    Button(
                        modifier = Modifier.weight(1F),
                        onClick = {
                            val current = Clock.System.now().toEpochMilliseconds()
                            onOKClick.invoke(
                                folder?.copy(category = name.value, updatedAt = current) ?: Folder(
                                    category = name.value,
                                    createdAt = current,
                                    updatedAt = current,
                                    isDefault = false
                                )
                            )
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDialog2(
    folder: Folder,
    onDismissRequest: () -> Unit,
    onModifyClick: (Folder) -> Unit,
    onDeleteClick: (Folder) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .padding(bottom = 8.dp),
                    onClick = { onDeleteClick.invoke(folder) }) {
                    Text(text = "삭제하기")
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .padding(top = 8.dp),
                    onClick = {
                        onModifyClick.invoke(
                            folder.copy(
                                updatedAt = Clock.System.now().toEpochMilliseconds()
                            )
                        )
                    }) {
                    Text(text = "수정하기")
                }
            }
        }
    }
}