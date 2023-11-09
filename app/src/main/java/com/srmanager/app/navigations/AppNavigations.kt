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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi


import com.srmanager.app.SplashScreen
import com.srmanager.app.home.GuestPreAccountCreationInfoScreen
import com.srmanager.app.home.HomeScreen

import com.srmanager.auth_presentation.forgot_pass.ForgetPassEmailInput
import com.srmanager.auth_presentation.forgot_pass.ForgetPasswordCheckYourMailScreen
import com.srmanager.auth_presentation.login.SignInScreen
import com.srmanager.auth_presentation.registration.SignUpScreen


import com.srmanager.auth_presentation.screens.PasswordUpdatedScreen
import com.srmanager.auth_presentation.verify.VerifiedEmailDoneScreen
import com.srmanager.auth_presentation.verify.VerifyEmailOTPScreen
import com.srmanager.core.common.navigation.Route

import com.srmanager.core.common.R as CommonR


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun IPApp(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    var isChangingEmail = remember { false }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Route.SPLASH) {
                SplashScreen(toHome = {
                    navController.navigate(Route.HOME) {
                        popUpTo(navController.graph.id)
                    }
                }, toLogin = {
                    navController.navigate(Route.SIGN_IN) {
                        popUpTo(navController.graph.id)
                    }
                })
            }

            composable(route = Route.HOME) {
                HomeScreen()
            }



            composable(
                route = Route.VERIFY_OTP_EMAIL + "/{email}/{source}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType },
                    navArgument("source") { type = NavType.StringType }
                )
            ) {
                VerifyEmailOTPScreen(
                    navController = navController,
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




            composable(route = Route.GUEST_PRE_ACC_CREATION_INFO) {
                GuestPreAccountCreationInfoScreen(onAccountCreateButtonClick = {
                    navController.navigate(Route.SIGN_UP)
                }, onFinished = {
//                    val notificationManager: NotificationManager =
//                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    if (notificationManager.isNotificationPolicyAccessGranted && notificationManager.areNotificationsEnabled()) {
//                        navController.navigate(Route.HOME)
//                    } else {
                    navController.navigate(Route.HOME)
                    // }

                })
            }
            composable(route = Route.SIGN_UP) {
                SignUpScreen(
                    snackBarHostState,
                    navController,
                    redirectVerifyScreen = { email, source ->
                        navController.navigate(Route.VERIFY_OTP_EMAIL + "/$email" + "/$source")
                    }
                )
            }

            composable(route = Route.VERIFIED_EMAIL) {
                VerifiedEmailDoneScreen(navController)
            }
            composable(route = Route.SIGN_IN) {
                SignInScreen(
                    snackbarHostState = snackBarHostState,
                    navController = navController,
                    onBack = {
                        //navController.navigate(Route.AUTH_CHOOSE)
                    },
                    toHome = {
                        navController.navigate(Route.HOME) {
                            popUpTo(navController.graph.id)
                        }
                    }
                )
            }
            composable(route = Route.FORGET_PASS_EMAIL_INPUT) {
                ForgetPassEmailInput(navController, snackBarHostState)
            }
            composable(
                route = Route.FORGET_PASS_CHECK_YOUR_MAIL + "/{email}",
                arguments = listOf(navArgument("email") {
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->

                val email = navBackStackEntry.arguments?.getString("email") ?: ""

                ForgetPasswordCheckYourMailScreen(email) {
                    navController.navigate(Route.FORGET_PASS_NEW_PASS + "/${it}")
                }
            }
            composable(
                route = Route.FORGET_PASS_NEW_PASS + "/{email}",
                arguments = listOf(navArgument("email") {
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->

                /* val email = navBackStackEntry.arguments?.getString("email") ?: ""

                ForgetPassSetNewPassScreen(email, navController, snackBarHostState) */

                navController.popBackStack(Route.SIGN_IN, inclusive = false)

            }
            composable(route = Route.FORGET_PASS_UPDATE) {
                PasswordUpdatedScreen(actionTextResId = CommonR.string.continues_as_login) {
                    navController.navigate(Route.SIGN_IN)
                }
            }
            composable(route = Route.PASS_UPDATE) {
                PasswordUpdatedScreen(actionTextResId = CommonR.string.done) {
                    navController.popBackStack(Route.HOME, inclusive = false)
                }
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


