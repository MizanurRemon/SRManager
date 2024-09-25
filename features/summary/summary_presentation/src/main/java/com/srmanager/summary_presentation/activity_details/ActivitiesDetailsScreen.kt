package com.srmanager.summary_presentation.activity_details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.MonthPicker
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.SalesmanInfo
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun ActivitiesDetailsScreen(
    onBack: () -> Unit,
    uiEvent: Flow<UiEvent>,
    onEvent: (ActivitiesDetailsEvent) -> Unit,
    state: ActivitiesDetailsState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.back
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.r())
                .padding(top = 10.r()),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(CommonR.string.select_month),
                    style = bodyRegularTextStyle,
                    modifier = Modifier.padding(bottom = 5.r())
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(15.r())
                        )
                        .fillMaxWidth()
                        .border(
                            width = 1.r(),
                            color = Color.LightGray,
                            shape = RoundedCornerShape(15.r())
                        )
                        .padding(top = 5.r())
                        .clickable {
                            onEvent(
                                ActivitiesDetailsEvent.OnMonthSelectionDialogOpen(
                                    true
                                )
                            )
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.r())
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${state.selectedMonth.first}, ${state.selectedMonth.second}",
                            style = bodyRegularTextStyle
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(DesignSystemR.drawable.ic_calendar),
                            contentDescription = null
                        )

                    }
                }

                Spacer(modifier = Modifier.height(10.r()))

                SalesmanInfo(salesManName = state.salesmanName, salesManCode = state.salesmanCode)

                Spacer(modifier = Modifier.height(10.r()))


            }

        }
    }

    if (state.isMonthSelectionDialogOpen) {
        MonthPicker(
            openDialog = remember { mutableStateOf(true) },
            cancelClicked = {
                onEvent(ActivitiesDetailsEvent.OnMonthSelectionDialogOpen(isOpened = false))
            },
            confirmButtonCLicked = {
                onEvent(ActivitiesDetailsEvent.OnMonthSelection(it))
            },
            selectedMonth = state.selectedMonth
        )
    }


}


@Composable
@Preview
fun PreviewActivitiesDetailsScreen() {
    ActivitiesDetailsScreen(
        onBack = {

        },
        uiEvent = flow { },
        onEvent = {},
        state = ActivitiesDetailsState()
    )
}