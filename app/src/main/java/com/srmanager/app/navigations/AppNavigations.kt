package com.srmanager.app.navigations


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.srmanager.app.home.HomeScreen
import com.srmanager.app.home.HomeViewModel
import com.srmanager.app.splash_screen.SplashScreen
import com.srmanager.auth_presentation.login.SignInScreen
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.network.dto.Outlet
import com.srmanager.order_presentation.order.OrderScreen
import com.srmanager.order_presentation.products.OrderProductsScreen
import com.srmanager.order_presentation.products.ProductsEvent
import com.srmanager.order_presentation.products.ProductsViewModel
import com.srmanager.order_presentation.selected_products.SelectedProductsScreen
import com.srmanager.order_presentation.selected_products.SelectedProductsViewModel
import com.srmanager.order_presentation.signature.SignatureEvent
import com.srmanager.order_presentation.signature.SignatureScreen
import com.srmanager.order_presentation.signature.SignatureViewModel
import com.srmanager.outlet_presentation.dashboard.OutletDashboardScreen
import com.srmanager.outlet_presentation.maps.multiple.AllOutletMapScreen
import com.srmanager.outlet_presentation.maps.single.MapScreen
import com.srmanager.outlet_presentation.outlet.OutletScreen
import com.srmanager.outlet_presentation.outlet_add.OutletAddScreen
import com.srmanager.outlet_presentation.outlet_add.OutletAddViewModel
import com.srmanager.outlet_presentation.outlet_checkout.OutletCheckOutEvent
import com.srmanager.outlet_presentation.outlet_checkout.OutletCheckOutViewModel
import com.srmanager.outlet_presentation.outlet_checkout.OutletCheckoutScreen
import com.srmanager.outlet_presentation.outlet_details.OutletDetailsEvent
import com.srmanager.outlet_presentation.outlet_details.OutletDetailsScreen
import com.srmanager.outlet_presentation.outlet_details.OutletDetailsViewModel
import com.srmanager.report_presentation.report.ReportScreen
import com.srmanager.summary_presentation.VisitingSummaryScreen
import com.srmanager.summary_presentation.activity_details.ActivitiesDetailsScreen
import com.srmanager.summary_presentation.activity_details.ActivitiesDetailsViewModel
import com.srmanager.summary_presentation.activity_summary.ActivitySummaryScreen
import com.srmanager.summary_presentation.activity_summary.ActivitySummaryViewModel
import com.srmanager.summary_presentation.productivity_status.outlet_productivity.OutletProductivityScreen
import com.srmanager.summary_presentation.productivity_status.outlet_productivity.OutletProductivityViewModel

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    var outletDetails: Outlet? = null

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = Color.White
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.OUTLET_PRODUCTIVITY,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Route.SPLASH) {
                SplashScreen(toHome = {
                    navController.navigate(Route.HOME) {
                        popUpTo(navController.graph.id)
                    }
                }, toLogin = {
                    navController.navigate(Route.SIGN_IN) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                })
            }

            composable(route = Route.HOME) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    navController = navController,
                    onMapClick = {
                        navController.navigate(Route.OUTLET_MAP)
                    },
                    onOutletClick = {
                        navController.navigate(Route.OUTLET)
                    },
                    onMyOrderClick = {
                        navController.navigate(Route.ORDER)
                    },
                    onVisitingSummaryClick = {
                        navController.navigate(Route.VISITING_SUMMARY)
                    },
                    state = viewModel.state,
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent
                )
            }


            composable(route = Route.SIGN_IN) {
                SignInScreen(
                    snackbarHostState = snackBarHostState,
                    navController = navController,
                    toHome = {
                        navController.navigate(Route.HOME) {
                            popUpTo(navController.graph.id)
                        }
                    })
            }


            composable(route = Route.OUTLET) {
                OutletScreen(onBack = {
                    navController.navigateUp()
                }, onAddClick = {
                    navController.navigate(Route.OUTLET_ADD)
                }, onItemClick = { data ->
                    outletDetails = data
                    navController.navigate(Route.OUTLET_DASHBOARD)
                }, onLocationClick = { data ->
                    outletDetails = data
                    navController.navigate(Route.MAP)
                }, onCheckOutClick = {
                    navController.navigate(Route.OUTLET_CHECKOUT)
                },
                    snackbarHostState = snackBarHostState
                )
            }

            composable(route = Route.OUTLET_ADD) {
                val viewmodel = hiltViewModel<OutletAddViewModel>()
                OutletAddScreen(
                    snackbarHostState = snackBarHostState,
                    onBack = { navController.navigateUp() },
                    state = viewmodel.state,
                    uiEvent = viewmodel.uiEvent,
                    onEvent = viewmodel::onEvent
                )
            }

            composable(route = Route.OUTLET_DETAILS) {
                val viewModel = hiltViewModel<OutletDetailsViewModel>()
                viewModel.onEvent(
                    OutletDetailsEvent.OnOutletIdSetup(
                        outletID = outletDetails?.id ?: 0
                    )
                )


                OutletDetailsScreen(
                    snackbarHostState = snackBarHostState,
                    onBack = { navController.navigateUp() },
                    state = viewModel.state,
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent
                )
            }

            composable(route = Route.REPORT) {
                ReportScreen(onBack = { navController.navigateUp() })
            }

            composable(route = Route.ORDER) {
                OrderScreen(
                    onBack = {
                        navController.navigateUp()
                    }
                )
            }

            composable(route = Route.MAP) {

                MapScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    outletDetails = outletDetails
                )
            }

            composable(route = Route.OUTLET_CHECKOUT) {
                val viewModel = hiltViewModel<OutletCheckOutViewModel>()

                viewModel.onEvent(
                    OutletCheckOutEvent.OnOutletInfoSetUp(
                        outletID = outletDetails!!.id.toString(),
                        latitude = outletDetails!!.latitude,
                        longitude = outletDetails!!.longitude
                    )
                )

                OutletCheckoutScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    snackbarHostState = snackBarHostState,
                    state = viewModel.state,
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent
                )
            }

            composable(route = Route.OUTLET_DASHBOARD) {
                OutletDashboardScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    onGridItemClick = { route, data ->
                        outletDetails = data
                        if (route == Route.PRODUCTS_ITEMS) {
                            navController.navigate(route) {
                                popUpTo(Route.OUTLET_DASHBOARD) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.navigate(route)
                        }
                    },
                    outletDetails = outletDetails
                )
            }

            composable(route = Route.OUTLET_MAP) {
                AllOutletMapScreen(onBack = {
                    navController.navigateUp()
                })
            }


            composable(route = Route.PRODUCTS_ITEMS) {
                val viewModel = hiltViewModel<ProductsViewModel>()

                viewModel.onEvent(
                    ProductsEvent.OnSetOutletID(
                        id = outletDetails!!.id.toString(),
                        customerId = outletDetails!!.customerId.toString()
                    )
                )

                OrderProductsScreen(onBack = {
                    navController.navigateUp()
                }, onNextClick = {
                    navController.navigate(Route.SELECTED_PRODUCTS_SCREEN)
                }, state = viewModel.state,
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent
                )
            }

            composable(route = Route.SIGNATURE_SCREEN) {
                val viewModel = hiltViewModel<SignatureViewModel>()

                viewModel.onEvent(
                    SignatureEvent.OnOutletDetailsEvent(
                        outletDetails = outletDetails!!
                    )
                )

                SignatureScreen(
                    state = viewModel.state,
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent,
                    onBack = {
                        navController.navigateUp()
                    }, onSuccess = {
                        navController.navigate(Route.OUTLET_DASHBOARD) {
                            popUpTo(Route.PRODUCTS_ITEMS) {
                                inclusive = true
                            }
                        }
                    },
                    snackbarHostState = snackBarHostState
                )
            }

            composable(route = Route.SELECTED_PRODUCTS_SCREEN) {
                val viewModel = hiltViewModel<SelectedProductsViewModel>()
                SelectedProductsScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    state = viewModel.state,
                    onEvent = viewModel::onEvent,
                    onNextClick = {
                        navController.navigate(Route.SIGNATURE_SCREEN)
                    }
                )
            }

            composable(route = Route.VISITING_SUMMARY) {
                VisitingSummaryScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    onActivitySummaryClick = {
                        navController.navigate(Route.ACTIVITY_SUMMARY)
                    },
                    onActivitiesDetailsClick = {
                        navController.navigate(Route.ACTIVITY_DETAILS)
                    },
                    onProductivityStatusClick = {}
                )
            }

            composable(route = Route.ACTIVITY_SUMMARY) {
                val viewModel = hiltViewModel<ActivitySummaryViewModel>()
                ActivitySummaryScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    state = viewModel.state,
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent
                )
            }

            composable(route = Route.ACTIVITY_DETAILS) {
                val viewModel = hiltViewModel<ActivitiesDetailsViewModel>()

                ActivitiesDetailsScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent,
                    state = viewModel.state
                )
            }

            composable(route = Route.OUTLET_PRODUCTIVITY) {
                val viewModel = hiltViewModel<OutletProductivityViewModel>()

                OutletProductivityScreen (
                    onBack = {
                        navController.navigateUp()
                    },
                    uiEvent = viewModel.uiEvent,
                    onEvent = viewModel::onEvent,
                    state = viewModel.state
                )
            }
        }
    }

}


