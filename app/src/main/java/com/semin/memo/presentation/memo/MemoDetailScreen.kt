package com.semin.memo.presentation.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semin.memo.domain.memo.Memo
import com.semin.memo.presentation.common.topbar.MemoDetailTopBar
import com.semin.memo.presentation.util.MemoDeleteSuccessException

@Composable
fun MemoDetailScreen(
    modifier: Modifier = Modifier,
    onShowSnackBar: (Throwable?) -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: (Memo) -> Unit,
    onBackClick: () -> Unit,
    memoId: Long,
    viewModel: MemoViewModel = hiltViewModel()
) {
    val memo by viewModel.currentMemo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setCurrentMemoId(memoId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        MemoDetailTopBar(
            onBackClick = onBackClick,
            onEditClick = {
                onEditClick.invoke(memo)
            },
            onDeleteClick = {
                viewModel.deleteMemo()
                onShowSnackBar.invoke(MemoDeleteSuccessException())
                onDeleteClick.invoke()
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = memo.title,
            onValueChange = {},
            enabled = false,
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
                .weight(1F)
                .verticalScroll(rememberScrollState()),
            value = memo.content,
            onValueChange = {},
            enabled = false,
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