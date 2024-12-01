package com.semin.memo.presentation.common.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.semin.memo.presentation.main.MainNavigator
import com.semin.memo.presentation.navigation.Route
import com.semin.memo.utils.Logs

@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onDrawerOpen: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val currentRoute =
        navigator.navController.currentBackStackEntryAsState().value?.destination?.route?.split("/")
            ?.first()

    Logs.e("current::$currentRoute")

    when (currentRoute) {
        Route.Memo.route -> {
            MemoTopBar(
                onDrawerOpen = onDrawerOpen
            )
        }

        Route.MemoUpsert.dataClassRoute -> {
            AddMemoTopBar(
                onBackClick = onBackClick
            )
        }

        Route.MemoDetail.dataClassRoute -> {
            MemoDetailTopBar(
                onBackClick = { navigator.popBackStack() },
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }

        else -> {

        }
    }
}

@Composable
internal fun MemoTopBar(
    modifier: Modifier = Modifier,
    onDrawerOpen: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
            .height(56.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.0F)
                .clickable {
                    onDrawerOpen.invoke()
                }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Drawer",
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Memo",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
internal fun AddMemoTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .aspectRatio(1.0F)
                .clickable {
                    onBackClick.invoke()
                }
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Add Memo",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun MemoDetailTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .aspectRatio(1.0F)
                .clickable {
                    onBackClick.invoke()
                }
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Memo Detail",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .aspectRatio(2F)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        onDeleteClick.invoke()
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Edit",
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        onEditClick.invoke()
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                )
            }
        }


    }
}

@Preview
@Composable
fun TopBarTest() {
    MemoDetailTopBar()
}