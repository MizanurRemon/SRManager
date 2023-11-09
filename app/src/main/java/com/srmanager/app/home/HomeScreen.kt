package com.srmanager.app.home

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.srmanager.core.designsystem.*
import com.srmanager.core.designsystem.components.*
import com.srmanager.core.designsystem.theme.*
import com.srmanager.core.ui.DevicePreviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(

) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppToolbarCompose(
            onClick = {

            },
            icon = DesignSystemR.drawable.ic_menu,
            title = CommonR.string.app_name
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@DevicePreviews
fun PreviewHomeScreen() {
    HomeScreen()
}