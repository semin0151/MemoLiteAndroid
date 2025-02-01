package com.semin.memo.presentation.memo

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.semin.memo.domain.memo.Memo
import com.semin.memo.presentation.navigation.Route

fun NavGraphBuilder.memoNavGraph(
    onShowSnackBar: (Throwable?) -> Unit,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: (Memo) -> Unit,
    onItemClick: (Memo) -> Unit,
    onFolderUpsert: () -> Unit
) {
    composable<Route.Memo> {
        MemoScreen(
            onShowSnackBar = onShowSnackBar,
            onAddClick = onAddClick,
            onItemClick = onItemClick,
            onFolderUpsert = onFolderUpsert
        )
    }

    composable<Route.MemoUpsert> { navBackStackEntry ->
        val memoId = navBackStackEntry.toRoute<Route.MemoUpsert>().memoId
        val memoTitle = navBackStackEntry.toRoute<Route.MemoUpsert>().memoTitle
        val memoContent = navBackStackEntry.toRoute<Route.MemoUpsert>().memoContent

        MemoUpsertScreen(
            onShowSnackBar = onShowSnackBar,
            onBackClick = onBackClick,
            memoId = memoId,
            memoTitle = memoTitle,
            memoContent = memoContent
        )
    }

    composable<Route.MemoDetail> { navBackStackEntry ->
        val memoId = navBackStackEntry.toRoute<Route.MemoDetail>().memoId

        MemoDetailScreen(
            onShowSnackBar = onShowSnackBar,
            onDeleteClick = onDeleteClick,
            onEditClick = onEditClick,
            onBackClick = onBackClick,
            memoId = memoId
        )
    }

    composable<Route.FolderUpsert> {
        FolderUpsertScreen(
            onBackClick = onBackClick
        )
    }
}