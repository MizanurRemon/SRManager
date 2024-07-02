package com.srmanager.app.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.heading2TextStyle
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    toHome: () -> Unit,
    toLogin: () -> Unit
) {

    LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->

            when (event) {


                is UiEvent.Success -> {

                     toHome()

                }

                is UiEvent.ShowSnackbar -> {
                }

                is UiEvent.NavigateUp -> {

                     toLogin()
                    //toHome()

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
                .size(100.r())
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp))
        )

        Spacer(modifier = Modifier.height(10.r()))

        Text(
            text = stringResource(id = CommonR.string.app_name).uppercase(),
            style = heading2TextStyle
        )

        Spacer(modifier = Modifier.weight(1f))

        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = APP_DEFAULT_COLOR,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(20.r())
            )
        }


        Spacer(modifier = Modifier.height(40.r()))

    }
}