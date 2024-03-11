package com.srmanager.outlet_presentation.outlet_checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.ColorTextPrimary
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.boldBodyTextStyle
import com.srmanager.core.designsystem.w
import com.srmanager.core.network.dto.Outlet
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun OutletCheckoutScreen(
    onBack: () -> Unit,
    viewModel: OutletCheckOutViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    outletDetails: Outlet?
) {


    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {

                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short,
                    )
                }

                is UiEvent.NavigateUp -> {

                }
            }

        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            OutletCheckOutEvent.OnOutletLocationSetUp(
                outletDetails!!.latitude,
                outletDetails!!.longitude
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.outlet_checkout
        )

        if (viewModel.state.isLoading) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = APP_DEFAULT_COLOR,
                    modifier = Modifier
                        .size(20.r())
                        .padding(top = 10.r())
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.r())
                    .verticalScroll(rememberScrollState()),

                ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(OutletCheckOutEvent.OnCardEvent)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ), elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ), border = BorderStroke(
                        width = 1.dp, color = if (viewModel.state.isReasonSelectionError) {
                            Color.Red
                        } else {
                            Color.Transparent
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.w()),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.state.selectedReason,
                            style = bodyRegularTextStyle.copy(
                                color = ColorTextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = ""
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        OutlinedTextField(
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                cursorColor = Color.Black,
                                focusedBorderColor = if (viewModel.state.isDescriptionEmpty) Color.Red else Color.White,
                                unfocusedBorderColor = if (viewModel.state.isDescriptionEmpty) Color.Red else Color.White,
                            ),
                            value = viewModel.state.description,
                            onValueChange = {
                                viewModel.onEvent(OutletCheckOutEvent.OnRemarksEnter(it))
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = CommonR.string.remarks),
                                    style = bodyRegularTextStyle.copy(color = if (viewModel.state.isDescriptionEmpty) Color.Red else ColorTextFieldPlaceholder)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .padding(top = 10.dp)
                                .shadow(elevation = 1.dp, shape = RoundedCornerShape(15.dp))
                        )

                        Spacer(modifier = Modifier.height(10.r()))

                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(id = CommonR.string.remaining) + ":" + viewModel.state.remainingWords,
                                textAlign = TextAlign.Center,
                                style = bodyRegularTextStyle.copy(
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(20.r()))

                        Row {
                            Text(text = stringResource(id = CommonR.string.location))
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${viewModel.state.latitude}, ${viewModel.state.longitude}",
                                style = boldBodyTextStyle.copy(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.r()))

                        Row {
                            Text(text = stringResource(id = CommonR.string.distance_from_outlet))
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${viewModel.state.distance.toString()} m",
                                style = boldBodyTextStyle.copy(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(50.r()))

                        AppActionButtonCompose(
                            stringId = CommonR.string.done,
                            onActionButtonClick = {
                                viewModel.onEvent(OutletCheckOutEvent.OnSubmitClick(outletDetails!!.id.toString()))
                            })
                    }

                    if (viewModel.state.reasonItemClicked) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.r())
                                .clickable {
                                    viewModel.state.reasonItemClicked = false
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE0F1E6),
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                        ) {
                            repeat(viewModel.state.checkOutStatusList.size) { index ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.onEvent(
                                                OutletCheckOutEvent.OnReasonSelect(
                                                    viewModel.state.checkOutStatusList[index]
                                                )
                                            )

                                        }
                                ) {
                                    Text(
                                        text = viewModel.state.checkOutStatusList[index].name,
                                        style = bodyRegularTextStyle.copy(
                                            color = ColorTextPrimary,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier
                                            .padding(15.dp)
                                    )

                                    if (index != viewModel.state.checkOutStatusList.size - 1) {
                                        Divider(
                                            color = Color.LightGray,
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (viewModel.state.isNetworkCalling) {
        LoadingDialog {

        }
    }

}

@Composable
@Preview
fun PreviewOutletCheckoutScreen() {
    val snackBarHostState = remember { SnackbarHostState() }
    val outletDetails = Outlet()
    OutletCheckoutScreen(
        onBack = {},
        snackbarHostState = snackBarHostState,
        outletDetails = outletDetails
    )
}
