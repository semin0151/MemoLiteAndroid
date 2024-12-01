package com.semin.memo.presentation.memo

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.semin.memo.domain.memo.Memo
import com.semin.memo.presentation.navigation.Route

fun NavGraphBuilder.memoNavGraph(
    innerPadding: PaddingValues,
    onShowSnackBar: (Throwable?) -> Unit,
    onAddClick: () -> Unit,
    backClicked: Boolean,
    deleteClicked: Boolean,
    editClicked: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: (Memo) -> Unit,
    onItemClick: (Memo) -> Unit,
) {
    composable<Route.Memo> {
        MemoScreen(
            innerPadding = innerPadding,
            onShowSnackBar = onShowSnackBar,
            onAddClick = onAddClick,
            onItemClick = onItemClick
        )
    }

    composable<Route.MemoUpsert> { navBackStackEntry ->
        val memoId = navBackStackEntry.toRoute<Route.MemoUpsert>().memoId
        val memoTitle = navBackStackEntry.toRoute<Route.MemoUpsert>().memoTitle
        val memoContent = navBackStackEntry.toRoute<Route.MemoUpsert>().memoContent

        MemoUpsertScreen(
            innerPadding = innerPadding,
            onShowSnackBar = onShowSnackBar,
            backClicked = backClicked,
            onBackClick = onBackClick,
            memoId = memoId,
            memoTitle = memoTitle,
            memoContent = memoContent
        )
    }

    composable<Route.MemoDetail> { navBackStackEntry ->
        val memoId = navBackStackEntry.toRoute<Route.MemoDetail>().memoId

        MemoDetailScreen(
            innerPadding = innerPadding,
            onShowSnackBar = onShowSnackBar,
            deleteClicked = deleteClicked,
            editClicked = editClicked,
            onDeleteClick = onDeleteClick,
            onEditClick = onEditClick,
            memoId = memoId
        )
    }
}