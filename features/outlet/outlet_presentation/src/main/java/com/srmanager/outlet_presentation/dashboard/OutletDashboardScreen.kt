package com.srmanager.outlet_presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.network.dto.Outlet
import com.srmanager.core.common.R as CommonR

@Composable
fun OutletDashboardScreen(
    onBack: () -> Unit,
    onGridItemClick: (route: String, outletDetails: Outlet?) -> Unit,
    outletDetails: Outlet? = null
) {
    Scaffold(
        topBar = {
            AppToolbarCompose(
                onClick = { onBack() },
                icon = DesignSystemR.drawable.ic_back,
                title = CommonR.string.back
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.r())
                ) {
                    items(OUTLET_DASHBOARD_MENUS.size) { index ->
                        GridItem(
                            OUTLET_DASHBOARD_MENUS[index],
                            onGridItemClick = { route, outletDetails ->
                                onGridItemClick(route, outletDetails)
                            },
                            outletDetails = outletDetails
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun GridItem(
    response: DashboardItemsResponse,
    onGridItemClick: (route: String, outletDetails: Outlet?) -> Unit,
    outletDetails: Outlet?
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onGridItemClick(response.route, outletDetails)
            }
            .padding(10.r()),
        colors = CardDefaults.cardColors(
            containerColor = APP_DEFAULT_COLOR,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.r()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                painter = painterResource(id = response.icon),
                contentDescription = "",
                modifier = Modifier.size(30.r()),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(10.r()))
            Text(
                text = stringResource(id = response.title),
                style = bodyBoldTextStyle.copy(
                    color = Color.White,
                    fontSize = 14.ssp(),
                    textAlign = TextAlign.Center
                ),
            )

        }
    }
}


@Composable
@Preview
fun PreviewOutletDashboardScreen() {
    //OutletDashboardScreen(onBack = {}, onGridItemClick = {})
}