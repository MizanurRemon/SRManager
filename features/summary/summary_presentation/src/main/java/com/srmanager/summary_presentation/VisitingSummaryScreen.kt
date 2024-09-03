package com.srmanager.summary_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
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
fun ActionButton(onItemClick: () -> Unit, title: Int) {
    Card(
        shape = RoundedCornerShape(15.r()),
        modifier = Modifier
            .clickable {
                onItemClick()
            }
            .padding(5.r())
            .shadow(
                elevation = 4.r(),
                spotColor = APP_DEFAULT_COLOR,
                shape = RoundedCornerShape(15.r())
            ),
        colors = CardDefaults.cardColors(containerColor = APP_DEFAULT_COLOR)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(title),
                style = bodyBoldTextStyle.copy(color = Color.White),
                modifier = Modifier.padding(vertical = 30.r())
            )
        }
    }
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