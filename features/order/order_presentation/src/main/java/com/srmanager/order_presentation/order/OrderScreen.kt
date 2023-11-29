package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.srmanager.core.designsystem.R
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(onBack: () -> Unit) {
    Scaffold(floatingActionButton = {

    }, floatingActionButtonPosition = FabPosition.End) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppToolbarCompose(
                onClick = { onBack() },
                icon = DesignSystemR.drawable.ic_back,
                title = CommonR.string.back
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = CommonR.string.order),
                    style = bodyBoldTextStyle.copy(color = Color.Black)
                )
            }
        }
    }
}