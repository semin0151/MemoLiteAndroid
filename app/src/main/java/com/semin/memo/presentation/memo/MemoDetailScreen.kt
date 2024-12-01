package com.semin.memo.presentation.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semin.memo.domain.memo.Memo
import com.semin.memo.presentation.util.MemoDeleteSuccessException

@Composable
fun MemoDetailScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onShowSnackBar: (Throwable?) -> Unit,
    deleteClicked: Boolean,
    editClicked: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: (Memo) -> Unit,
    memoId: Long,
    viewModel: MemoViewModel = hiltViewModel()
) {
    val memo by viewModel.currentMemo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setCurrentMemoId(memoId)
    }

    if(editClicked) {
        LaunchedEffect(Unit) {
            onEditClick.invoke(memo)
        }
    }

    if (deleteClicked) {
        LaunchedEffect(Unit) {
            viewModel.deleteMemo()
            onShowSnackBar.invoke(MemoDeleteSuccessException())
            onDeleteClick.invoke()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = memo.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        HorizontalDivider()

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            text = memo.content,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}