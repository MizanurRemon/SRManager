package com.srmanager.app.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.srmanager.core.designsystem.*
import com.srmanager.core.designsystem.components.*
import com.srmanager.core.designsystem.theme.*
import com.srmanager.core.ui.DevicePreviews
import kotlinx.coroutines.launch
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun HomeScreen(navController: NavHostController) {

    var drawerOpen by remember {
        mutableStateOf(false)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeModalDrawerSheet(
                scope = scope,
                drawerState = drawerState,
                navController = navController
            )

        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                AppToolbarCompose(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    icon = DesignSystemR.drawable.ic_menu,
                    title = CommonR.string.app_name
                )
            }
        }
    )

}



@Composable
@DevicePreviews
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}