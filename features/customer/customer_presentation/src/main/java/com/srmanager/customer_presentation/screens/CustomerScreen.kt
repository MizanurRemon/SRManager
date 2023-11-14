package com.srmanager.customer_presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(onBack: () -> Unit) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { },
            modifier = Modifier.padding(end = 20.r(), bottom = 40.r()),
            containerColor = APP_DEFAULT_COLOR
        ) {
            Icon(
                painterResource(id = DesignSystemR.drawable.ic_customer_add),
                contentDescription = null,
                modifier = Modifier.size(30.r()),
                tint = Color.White
            )
        }
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
                Text(text = "Customer add", style = bodyBoldTextStyle.copy(color = Color.Black))
            }
        }
    }
}

@Composable
@Preview
fun PreviewCustomerAddScreen() {
    CustomerScreen(onBack = {})
}