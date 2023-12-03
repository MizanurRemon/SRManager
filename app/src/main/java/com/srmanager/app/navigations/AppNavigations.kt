package com.srmanager.app.navigations


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.srmanager.app.splash_screen.SplashScreen
import com.srmanager.app.home.HomeScreen
import com.srmanager.auth_presentation.login.SignInScreen
import com.srmanager.auth_presentation.verify.VerifiedEmailDoneScreen
import com.srmanager.auth_presentation.verify.VerifyEmailOTPScreen
import com.srmanager.core.common.navigation.Route
import com.srmanager.order_presentation.order.OrderScreen
import com.srmanager.outlet_presentation.outlet.OutletScreen
import com.srmanager.outlet_presentation.outlet_add.OutletAddScreen
import com.srmanager.report_presentation.report.ReportScreen
import com.srmanager.core.common.R as CommonR


@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var isChangingEmail = remember { false }

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
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                })
            }

            composable(route = Route.HOME) {
                HomeScreen(navController)
            }



            composable(
                route = Route.VERIFY_OTP_EMAIL + "/{email}/{source}",
                arguments = listOf(navArgument("email") { type = NavType.StringType },
                    navArgument("source") { type = NavType.StringType })
            ) {
                VerifyEmailOTPScreen(navController = navController,
                    snackBarHostState,
                    email = it.arguments?.getString("email") ?: "",
                    source = it.arguments?.getString("source") ?: "",
                    onSubmit = {
                        if (isChangingEmail) {
                            isChangingEmail = false
                            navController.navigate(Route.EMAIL_UPDATED)
                        } else {
                            navController.navigate(Route.VERIFIED_EMAIL)
                        }

                    })
            }


            composable(route = Route.VERIFIED_EMAIL) {
                VerifiedEmailDoneScreen(navController)
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


            composable(
                route = Route.FORGET_PASS_NEW_PASS + "/{email}",
                arguments = listOf(navArgument("email") {
                    type = NavType.StringType
                })
            ) {

                navController.popBackStack(Route.SIGN_IN, inclusive = false)

            }


            composable(route = Route.OUTLET) {
                OutletScreen(onBack = {
                    navController.navigateUp()
                }) {
                    navController.navigate(Route.OUTLET_ADD)
                }
            }

            composable(route = Route.OUTLET_ADD) {
                OutletAddScreen(onBack = { navController.navigateUp() })
            }

            composable(route = Route.REPORT) {
                ReportScreen(onBack = { navController.navigateUp() })
            }

            composable(route = Route.ORDER) {
                OrderScreen(onBack = {
                    navController.navigateUp()
                })
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


