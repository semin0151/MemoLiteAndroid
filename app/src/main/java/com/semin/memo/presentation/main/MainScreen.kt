package com.semin.memo.presentation.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.nativead.NativeAd
import com.semin.memo.presentation.ads.CallNativeAd
import com.semin.memo.presentation.ads.loadNativeAd
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

    MainScreenContent(
        navigator = navigator,
        onShowSnackBar = onShowSnackBar,
        snackBarHostState = snackBarHostState
    )
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowSnackBar: (Throwable?) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loadNativeAd(context, "ca-app-pub-9173427452000522/6333761094") {
            nativeAd = it
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if(nativeAd != null) {
                CallNativeAd(nativeAd!!)
            }
        },
        content = { innerPadding ->
            MainNavHost(
                navigator = navigator,
                innerPadding = innerPadding,
                onShowSnackBar = onShowSnackBar
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}