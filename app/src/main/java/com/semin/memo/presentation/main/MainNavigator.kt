package com.semin.memo.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.semin.memo.presentation.navigation.Route

@Stable
internal class MainNavigator(
    val navController: NavHostController
) {
    val startDestination: Route = Route.Memo

    fun navigateToUpsertMemo(
        memoId: Long = 0L,
        memoTitle: String = "",
        memoContent: String = ""
    ) {
        navController.navigate(
            Route.MemoUpsert(
                memoId = memoId,
                memoTitle = memoTitle,
                memoContent = memoContent,
            )
        )
    }

    fun navigateToMemoDetail(memoId: Long) {
        navController.navigate(Route.MemoDetail(memoId))
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}