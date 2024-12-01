package com.semin.memo.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.semin.memo.presentation.memo.memoNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    innerPadding: PaddingValues,
    onShowSnackBar: (Throwable?) -> Unit,
    backClicked: Boolean,
    deleteClicked: Boolean,
    editClicked: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            memoNavGraph(
                innerPadding = innerPadding,
                onShowSnackBar = onShowSnackBar,
                onAddClick = { navigator.navigateToUpsertMemo() },
                backClicked = backClicked,
                deleteClicked = deleteClicked,
                editClicked = editClicked,
                onBackClick = onBackClick,
                onDeleteClick = onDeleteClick,
                onEditClick = { memo ->
                    navigator.navigateToUpsertMemo(
                        memoId = memo.primaryKey,
                        memoTitle = memo.title,
                        memoContent = memo.content
                    )
                    onEditClick.invoke()
                },
                onItemClick = { memo -> navigator.navigateToMemoDetail(memoId = memo.primaryKey) }
            )
        }
    }
}