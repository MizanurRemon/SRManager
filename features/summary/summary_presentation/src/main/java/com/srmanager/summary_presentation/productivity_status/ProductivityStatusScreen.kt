package com.srmanager.summary_presentation.productivity_status

import com.srmanager.core.designsystem.components.ActionButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@Composable
fun ProductivityStatusScreen(
    onBack: () -> Unit,
    onProductProductivity: () -> Unit,
    onOutletProductivity: () -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppToolbarCompose(
                onClick = { onBack() },
                icon = DesignSystemR.drawable.ic_back,
                title = CommonR.string.back
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.r())

                ) {
                    item {
                        ActionButton(
                            onItemClick = {
                                onProductProductivity()
                            },
                            title = CommonR.string.product_productivity,
                        )

                    }
                    item {

                        ActionButton(
                            onItemClick = {
                                onOutletProductivity()
                            },
                            title = CommonR.string.outlet_productivity,
                        )
                    }


                }
            }
        }
    )
}


@Composable
@Preview
fun PreviewProductivityStatusScreen() {
    ProductivityStatusScreen(onBack = {}, onProductProductivity = {}, onOutletProductivity = { })
}