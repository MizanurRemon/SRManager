package com.srmanager.order_presentation.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.components.AppToolbarCompose

@Composable
fun OrderProductsScreen(onBack: () -> Unit) {
    Scaffold(topBar = {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.back
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "Items")
        }
    }
}