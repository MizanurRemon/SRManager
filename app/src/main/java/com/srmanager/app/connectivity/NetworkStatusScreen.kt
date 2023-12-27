package com.srmanager.app.connectivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.designScreenWidth
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkStatusScreen() {
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCallback = remember { NetworkCallbackImpl() }
    val isNetworkAvailable by networkCallback.isNetworkAvailable

    DisposableEffect(connectivityManager) {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(DesignR.raw.ic_no_network))


    if (!isNetworkAvailable) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = AppBrush)
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 18.dp, end = 18.dp),
                floatingActionButton = {
                    AppActionButtonCompose(
                        stringId = CommonR.string.reload,
                        modifier = Modifier.padding(36.dp)
                    ) {
                        if (!isNetworkAvailable) {

                            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))

                        }
                    }
                },
                floatingActionButtonPosition = FabPosition.Center
            ) {
                Column(
                    //verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LottieAnimation(
                        modifier = Modifier.height(400.r()),
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                    )
                    Text(
                        text = stringResource(id = CommonR.string.no_active_internet_connection),
                        modifier = Modifier.padding(bottom = 50.dp),
                        style = subHeading1TextStyle.copy(fontSize = 28.sp),
                    )

                    Text(
                        text = stringResource(id = CommonR.string.no_internet_connection_text),
                        textAlign = TextAlign.Center,
                        style = bodyRegularTextStyle
                    )
                }
            }
        }

    }

}

@Composable
@Preview
fun PreviewNetworkStatusScreen() {
    NetworkStatusScreen()
}