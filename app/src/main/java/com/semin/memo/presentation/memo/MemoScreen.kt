package com.semin.memo.presentation.memo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.semin.memo.domain.memo.Memo
import com.semin.memo.utils.formattedDateTime

@Composable
fun MemoScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onShowSnackBar: (Throwable?) -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (Memo) -> Unit,
    viewModel: MemoViewModel = hiltViewModel()
) {
    val memoList: List<Memo> by viewModel.memoList.collectAsStateWithLifecycle()
    val memoIsEmpty by remember { derivedStateOf { memoList.isEmpty() } }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (memoIsEmpty) {
            MemoEmptyScreen()
        } else {
            MemoListScreen(
                memoList = memoList,
                onItemClick = onItemClick
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp),
            onClick = onAddClick
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Add,
                contentDescription = "FAB",
            )
        }
    }
}

@Composable
fun MemoEmptyScreen(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight(),
        text = "No Memo",
        textAlign = TextAlign.Center,
    )
}

@Composable
fun MemoListScreen(
    modifier: Modifier = Modifier,
    memoList: List<Memo>,
    onItemClick: (Memo) -> Unit
) {
    LazyColumn {
        items(memoList) {
            MemoListItemScreen(
                memo = it,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun MemoListItemScreen(
    modifier: Modifier = Modifier,
    memo: Memo,
    onItemClick: (Memo) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                onItemClick.invoke(memo)
            }
    ) {
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1F)
                .align(Alignment.CenterVertically),
            text = memo.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .padding(end = 10.dp)
                .widthIn(80.dp)
                .align(Alignment.CenterVertically),
            text = memo.updatedAt.formattedDateTime,
            textAlign = TextAlign.End,
            fontSize = 12.sp
        )
    }

    HorizontalDivider()
}

@Preview
@Composable
private fun MemoListItemPreview() {
    MemoListItemScreen(
        memo = Memo(
            title = "seminzzangseminzzangseminzzang",
            content = "hello world",
            createdAt = 0L,
            updatedAt = 0L
        ),
        onItemClick = {}
    )
}