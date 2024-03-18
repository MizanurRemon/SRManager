package com.srmanager.app.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.*
import com.srmanager.core.designsystem.components.*
import com.srmanager.core.designsystem.theme.*
import com.srmanager.core.ui.DevicePreviews
import com.srmanager.outlet_presentation.dashboard.GridItem
import kotlinx.coroutines.launch
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    onOutletClick: () -> Unit,
    onMapClick: () -> Unit
) {

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

                Column(modifier = Modifier.fillMaxSize()) {
                    /*Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    }*/

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize().padding(16.dp),

                        ) {
                        item {
                            HomeScreenActionButton(
                                onItemClick = {
                                    onOutletClick()
                                }, title = CommonR.string.outlet, icon = DesignSystemR.drawable.ic_shop
                            )

                        }
                        item {

                            HomeScreenActionButton(
                                onItemClick = {
                                    onMapClick()
                                },
                                title = CommonR.string.location,
                                icon = DesignSystemR.drawable.ic_location_pin
                            )
                        }
                    }

                }
            }
        }
    )

    if (viewModel.state.isLogOutLoading) {
        LoadingDialog {}
    }

}


@Composable
fun HomeScreenActionButton(onItemClick: () -> Unit, icon: Int, title: Int) {
    Card(
        modifier = Modifier
            .clickable {
                onItemClick()
            }
            .padding(10.r()),
        colors = CardDefaults.cardColors(
            containerColor = APP_DEFAULT_COLOR,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.r()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(30.r()),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(10.r()))
            Text(
                text = stringResource(id = title),
                style = bodyBoldTextStyle.copy(
                    color = Color.White,
                    fontSize = 14.ssp(),
                    textAlign = TextAlign.Center
                ),
            )

        }
    }
}


@Composable
@DevicePreviews
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController(), onOutletClick = {}, onMapClick = {})
}