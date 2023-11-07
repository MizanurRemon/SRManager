package com.srmanager.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.languageList
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.heading2TextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
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
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() { event ->

            when (event) {


                is UiEvent.Success -> {
                    delay(2000L)
                    toHome()

                }

                is UiEvent.ShowSnackbar -> {
                }

                is UiEvent.NavigateUp -> {
                    //delay(2000L)

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
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = DesignSystemR.drawable.app_icon
            ),
            contentDescription = "",
            modifier = Modifier
                .size(150.w())
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = stringResource(id = CommonR.string.app_name).uppercase(), style = heading2TextStyle)
    }
}