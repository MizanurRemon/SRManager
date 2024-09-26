package com.srmanager.summary_presentation.productivity_status.outlet_productivity

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.MonthPicker
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.SalesmanInfo
import com.srmanager.core.designsystem.components.table.DrawTable
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR


@Composable
fun OutletProductivityScreen(
    onBack: () -> Unit,
    state: OutletProductivityState,
    onEvent: (OutletProductivityEvent) -> Unit,
    uiEvent: Flow<UiEvent>
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .clickable {
                onEvent(OutletProductivityEvent.OnMonthSelectionDialogOpen(false))
            }
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
                                OutletProductivityEvent.OnMonthSelectionDialogOpen(
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

                OutlinedTextField(
                    singleLine = true,
                    value = state.search,
                    shape = RoundedCornerShape(16.r()),
                    onValueChange = {
                        onEvent(OutletProductivityEvent.OnSearchEvent(it))
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.r())
                        .border(
                            width = 1.r(),
                            shape = RoundedCornerShape(16.r()),
                            color = Color.LightGray
                        ),

                    placeholder = {
                        Text(
                            text = stringResource(CommonR.string.search),
                            style = bodyRegularTextStyle.copy(color = Color.Gray)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(20.r()),
                            tint = Color.Gray
                        )
                    },
                    textStyle = bodyRegularTextStyle.copy(textAlign = TextAlign.Start),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()

                        defaultKeyboardAction(ImeAction.Done)
                    }),
                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.r())
                ) {
                    MonthlyActivitiesDetails(state.searchedVisitingList)
                }

            }

        }
    }

    if (state.isMonthSelectionDialogOpen) {
        MonthPicker(
            openDialog = remember { mutableStateOf(true) },
            cancelClicked = {
                onEvent(OutletProductivityEvent.OnMonthSelectionDialogOpen(isOpened = false))
            },
            confirmButtonCLicked = {
                onEvent(OutletProductivityEvent.OnMonthSelection(it))
            },
            selectedMonth = state.selectedMonth
        )
    }

}

@Composable
fun MonthlyActivitiesDetails(visitingDetailsList: List<OutletProductivity>) {

    val tableData = visitingDetailsList.map {
        listOf(
            it.outletCode,
            it.outletName,
            it.numberOfVisit,
            it.numberOfOrder,
            it.totalOrderAmount
        )

    }

    val headerTitles = listOf(
        stringResource(CommonR.string.outlet_code),
        stringResource(CommonR.string.outlet_name),
        stringResource(CommonR.string.number_of_visit),
        stringResource(CommonR.string.number_of_order),
        stringResource(CommonR.string.total_order_amount)
    )


    DrawTable(
        data = tableData,
        enableTableHeaderTitles = true,
        disableVerticalDividers = false,
        headerTableTitles = headerTitles,
        headerTitlesBackGroundColor = Color.White,
        tableRowColors = listOf(
            Color.White,
            Color.White
        ),
        contentAlignment = Alignment.CenterStart,
        textAlign = TextAlign.Start,
        horizontalDividerColor = Color.LightGray,
        rowBorderColor = Color.Black,
        rowTextStyle = bodyRegularTextStyle.copy(
            color = Color.Black, fontSize = 12.ssp(), fontWeight = FontWeight.Light
        ),
        headerTitlesTextStyle = bodyRegularTextStyle.copy(
            color = Color.Black, fontSize = 12.ssp(), fontWeight = FontWeight.Bold
        )
    )
}


@Composable
@Preview
fun PreviewOutletProductivityScreen() {
    OutletProductivityScreen(
        onBack = {},
        state = OutletProductivityState(),
        onEvent = {},
        uiEvent = flow { }
    )
}