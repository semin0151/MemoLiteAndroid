package com.semin.memo.presentation.memo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.semin.memo.presentation.util.MemoSaveSuccessException
import kotlin.math.min

@Composable
fun MemoUpsertScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onShowSnackBar: (Throwable?) -> Unit,
    backClicked: Boolean,
    onBackClick: () -> Unit,
    memoId: Long,
    memoTitle: String,
    memoContent: String,
    viewModel: MemoViewModel = hiltViewModel()
) {
    val oldTitle = remember { memoTitle }
    val oldContent = remember { memoContent }
    val title = remember { mutableStateOf(memoTitle) }
    val content = remember { mutableStateOf(memoContent) }

    if (backClicked) {
        LaunchedEffect(Unit) {
            if (oldTitle != title.value || oldContent != content.value) {
                if (content.value.isNotEmpty()) {
                    if (title.value.isEmpty()) {
                        viewModel.upsertMemo(
                            memoId = memoId,
                            title = content.value.substring(0..min(20, content.value.length.dec())),
                            content = content.value
                        )
                    } else {
                        viewModel.upsertMemo(
                            memoId = memoId,
                            title = title.value,
                            content = content.value
                        )
                    }
                    onShowSnackBar.invoke(MemoSaveSuccessException())
                } else {
                    if (title.value.isNotEmpty()) {
                        viewModel.upsertMemo(
                            memoId = memoId,
                            title = title.value,
                            content = content.value
                        )
                        onShowSnackBar.invoke(MemoSaveSuccessException())
                    }
                }
            }

            onBackClick.invoke()
        }
    }

    BackHandler(enabled = true) {
        if (oldTitle != title.value || oldContent != content.value) {
            if (content.value.isNotEmpty()) {
                if (title.value.isEmpty()) {
                    viewModel.upsertMemo(
                        memoId = memoId,
                        title = content.value.substring(0..min(20, content.value.length.dec())),
                        content = content.value
                    )
                } else {
                    viewModel.upsertMemo(
                        memoId = memoId,
                        title = title.value,
                        content = content.value
                    )
                }
                onShowSnackBar.invoke(MemoSaveSuccessException())
            } else {
                if (title.value.isNotEmpty()) {
                    viewModel.upsertMemo(
                        memoId = memoId,
                        title = title.value,
                        content = content.value
                    )
                    onShowSnackBar.invoke(MemoSaveSuccessException())
                }
            }
        }
        onBackClick.invoke()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title.value,
            onValueChange = {
                title.value = it
            },
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.background
            )
        )

        HorizontalDivider()

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            value = content.value,
            onValueChange = {
                content.value = it
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.background
            )
        )
    }
}