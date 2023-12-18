package com.srmanager.app.navigations


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.srmanager.app.splash_screen.SplashScreen
import com.srmanager.app.home.HomeScreen
import com.srmanager.auth_presentation.login.SignInScreen
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.network.dto.Data
import com.srmanager.order_presentation.order.OrderScreen
import com.srmanager.outlet_presentation.dashboard.OutletDashboardScreen
import com.srmanager.outlet_presentation.outlet_checkout.OutletCheckoutScreen
import com.srmanager.outlet_presentation.maps.MapScreen
import com.srmanager.outlet_presentation.outlet.OutletScreen
import com.srmanager.outlet_presentation.outlet_add.OutletAddScreen
import com.srmanager.outlet_presentation.outlet_details.OutletDetailsScreen
import com.srmanager.report_presentation.report.ReportScreen

@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    var outletDetails: Data? = null

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.SPLASH,
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
                HomeScreen(navController)
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
                OutletAddScreen(
                    snackbarHostState = snackBarHostState,
                    onBack = { navController.navigateUp() })
            }

            composable(route = Route.OUTLET_DETAILS) {
                OutletDetailsScreen(
                    snackbarHostState = snackBarHostState,
                    onBack = { navController.navigateUp() }
                )
            }

            composable(route = Route.REPORT) {
                ReportScreen(onBack = { navController.navigateUp() })
            }

            composable(route = Route.ORDER) {
                OrderScreen(onBack = {
                    navController.navigateUp()
                })
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
                OutletCheckoutScreen(onBack = {
                    navController.navigateUp()
                })
            }

            composable(route = Route.OUTLET_DASHBOARD) {
                OutletDashboardScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                    onGridItemClick = { route, data ->
                        outletDetails = data
                        navController.navigate(route)
                    },
                    outletDetails = outletDetails
                )
            }

        }
    }

}

fun openOverlaySettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName)
    )
    context.startActivity(intent)
}


fun openAccessibilitySettings(context: Context) {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)

}


