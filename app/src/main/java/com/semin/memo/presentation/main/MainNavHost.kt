package com.semin.memo.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.semin.memo.presentation.memo.memoNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    innerPadding: PaddingValues,
    onShowSnackBar: (Throwable?) -> Unit
) {
    Box(
        modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            memoNavGraph(
                onShowSnackBar = onShowSnackBar,
                onAddClick = { navigator.navigateToUpsertMemo() },
                onBackClick = { navigator.popBackStack() },
                onDeleteClick = { navigator.popBackStack() },
                onEditClick = { memo ->
                    navigator.navigateToUpsertMemo(memo)
                },
                onItemClick = { memo -> navigator.navigateToMemoDetail(memoId = memo.primaryKey) },
                onFolderUpsert = {
                    navigator.navigateToFolderUpsert()
                }
            )
        }
    }
}