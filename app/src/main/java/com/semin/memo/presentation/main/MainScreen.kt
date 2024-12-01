package com.semin.memo.presentation.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.semin.memo.presentation.common.topbar.TopBar
import com.semin.memo.presentation.util.NotReadyException
import com.semin.memo.presentation.util.SnackBarException
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val onShowSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            when (throwable) {
                is SnackBarException -> {
                    snackBarHostState.showSnackbar(throwable.message)
                }
            }
        }
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { }
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    ) {
        MainScreenContent(
            navigator = navigator,
            onShowSnackBar = onShowSnackBar,
            onDrawerOpen = {
                coroutineScope.launch {
                    drawerState.open()
                }
            },
            snackBarHostState = snackBarHostState
        )
    }
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowSnackBar: (Throwable?) -> Unit,
    onDrawerOpen: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val backClicked = remember { mutableStateOf(false) }
    val editClicked = remember { mutableStateOf(false) }
    val deleteClicked = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                modifier = modifier,
                navigator = navigator,
                onDrawerOpen = { onShowSnackBar.invoke(NotReadyException()) },
                onBackClick = { backClicked.value = true },
                onEditClick = { editClicked.value = true },
                onDeleteClick = { deleteClicked.value = true }
            )
        },
        content = { innerPadding ->
            MainNavHost(
                navigator = navigator,
                innerPadding = innerPadding,
                onShowSnackBar = onShowSnackBar,
                backClicked = backClicked.value,
                deleteClicked = deleteClicked.value,
                editClicked = editClicked.value,
                onBackClick = {
                    navigator.popBackStack()
                    backClicked.value = false
                },
                onDeleteClick = {
                    navigator.popBackStack()
                    deleteClicked.value = false
                },
                onEditClick = {
                    editClicked.value = false
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}