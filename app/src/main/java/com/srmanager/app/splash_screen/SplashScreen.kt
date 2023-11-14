package com.srmanager.app.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.ColorPrimaryDark
import com.srmanager.core.designsystem.theme.bodyLightTextStyle
import com.srmanager.core.designsystem.theme.heading2TextStyle
import com.srmanager.core.designsystem.w
import kotlinx.coroutines.delay
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    toHome: () -> Unit,
    toLogin: () -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() { event ->

            when (event) {


                is UiEvent.Success -> {

                     toHome()

                }

                is UiEvent.ShowSnackbar -> {
                }

                is UiEvent.NavigateUp -> {

                     toLogin()

                }
            }

        }
    }



    Column(
        modifier = Modifier
            .background(brush = AppBrush)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        // verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.r()))
        Image(
            painter = painterResource(
                id = DesignSystemR.drawable.app_icon
            ),
            contentDescription = "",
            modifier = Modifier
                .size(100.w())
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp))
        )

        Spacer(modifier = Modifier.height(10.r()))

        Text(
            text = stringResource(id = CommonR.string.app_name).uppercase(),
            style = heading2TextStyle
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 5.r())
            ) {
                Icon(
                    Icons.Rounded.LocationOn,
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.r())
                )
                Spacer(modifier = Modifier.width(5.r()))

                Text(
                    text = stringResource(
                        id = if (viewModel.state.isLoading) CommonR.string.fetching_location else CommonR.string.current_location
                    ), style = bodyLightTextStyle.copy(color = Color.Gray, fontSize = 14.sp)
                )
            }

            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = APP_DEFAULT_COLOR,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.r())
                )
            } else {
                Text(
                    text = viewModel.state.address!!.value.toString(),
                    style = bodyLightTextStyle.copy(
                        letterSpacing = .2.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }


        Spacer(modifier = Modifier.height(40.r()))

    }
}