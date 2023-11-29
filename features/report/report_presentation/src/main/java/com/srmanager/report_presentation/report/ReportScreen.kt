package com.srmanager.report_presentation.report

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.srmanager.core.designsystem.R
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReportScreen(onBack: () -> Unit) {
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
                    text = stringResource(id = CommonR.string.report),
                    style = bodyBoldTextStyle.copy(color = Color.Black)
                )
            }
        }
    }
}