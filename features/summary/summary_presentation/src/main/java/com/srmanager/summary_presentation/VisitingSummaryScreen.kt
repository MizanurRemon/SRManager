package com.srmanager.summary_presentation

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
import com.srmanager.core.designsystem.components.ActionButton
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun VisitingSummaryScreen(
    onBack: () -> Unit,
    onActivitySummaryClick: () -> Unit,
    onActivitiesDetailsClick: () -> Unit,
    onProductivityStatusClick: () -> Unit
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
                                onActivitySummaryClick()
                            },
                            title = CommonR.string.activities_summary,
                        )

                    }
                    item {

                        ActionButton(
                            onItemClick = {
                                onActivitiesDetailsClick()
                            },
                            title = CommonR.string.activities_details,
                        )
                    }

                    item {
                        ActionButton(
                            onItemClick = {
                                onProductivityStatusClick()
                            },
                            title = CommonR.string.productivity_status,
                        )
                    }
                }
            }
        }
    )
}




@Composable
@Preview
fun PreviewVisitingSummaryScreen() {
    VisitingSummaryScreen(
        onBack = {},
        onActivitySummaryClick = {},
        onActivitiesDetailsClick = {},
        onProductivityStatusClick = {})
}