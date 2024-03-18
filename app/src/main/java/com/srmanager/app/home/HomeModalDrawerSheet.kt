package com.srmanager.app.home

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.srmanager.app.navigations.NavigationDrawer
import com.srmanager.core.designsystem.components.InfoItemCompose
import com.srmanager.core.designsystem.h
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.*
import com.srmanager.core.designsystem.w
import com.srmanager.core.ui.DevicePreviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeModalDrawerSheet(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavHostController,
    logOutClick: ()-> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ModalDrawerSheet(
        modifier = if (drawerState.isOpen) Modifier.width(364.w()) else Modifier,
        drawerTonalElevation = 0.dp,
        drawerShape = RectangleShape,
    ) {

        NavigationDrawer(currentDestination, onNavigationDrawerItemClick = {
            scope.launch {
                if (it.title == CommonR.string.log_out) {
                    logOutClick()
                } else {
                    navController.navigate(it.route)
                }


            }
        }, onCloseClick = {
            scope.launch {
                drawerState.close()
            }
        })
    }
}

