package com.srmanager.app.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.common.util.UiEvent
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
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    var drawerOpen by remember {
        mutableStateOf(false)
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() { event ->
            when (event) {
                is UiEvent.Success -> {
                    navController.navigate(Route.SIGN_IN) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }

                is UiEvent.ShowSnackbar -> {
                    /*snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short,
                    )*/
                }

                is UiEvent.NavigateUp -> {
                    navController.navigateUp()
                }

                else -> {}
            }

        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeModalDrawerSheet(
                scope = scope,
                drawerState = drawerState,
                navController = navController,
                logOutClick = {
                    viewModel.onEvent(HomeEvent.OnLogOut)
                }
            )

        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                AppHomeToolbarCompose(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    icon = DesignSystemR.drawable.ic_menu,
                    title = CommonR.string.app_name,
                    address = viewModel.state.address
                )
            }
        }
    )

    if (viewModel.state.isLogOutLoading){
        LoadingDialog {}
    }

}


@Composable
@DevicePreviews
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}