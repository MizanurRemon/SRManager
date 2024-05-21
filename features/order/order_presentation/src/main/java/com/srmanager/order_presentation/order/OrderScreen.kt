package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.changeDateFormat
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.ColorTextSecondary
import com.srmanager.core.designsystem.theme.MyDatePickerDialog
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.network.dto.Order
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    onBack: () -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val showCalenderDialog = remember {
        mutableStateOf(false)
    }

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
                .padding(horizontal = 10.r())
        ) {

            Spacer(modifier = Modifier.height(10.r()))

            OutlinedTextField(
                singleLine = true,
                value = viewModel.state.searchText,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()

                    defaultKeyboardAction(ImeAction.Done)
                }),
                onValueChange = {
                    viewModel.onEvent(OrderEvent.OnSearchEvent(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(10.r()))
                    .border(
                        width = 1.r(),
                        shape = RoundedCornerShape(10.r()),
                        color = Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.search_order),
                        style = bodyRegularTextStyle.copy(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = DesignSystemR.drawable.ic_calendar),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                //viewModel.onEvent(OrderEvent.OnCalenderTapEvent)
                                showCalenderDialog.value = true
                            }
                            .size(24.r())
                    )
                })

            val lazyColumnListState = rememberLazyListState()
            LazyColumn(
                state = lazyColumnListState,
            ) {
                items(viewModel.state.searchedOrderList) {order->
                    OrderItemCompose(item = order, onOrderNoClick = {
                        viewModel.onEvent(
                            OrderEvent.OnOrderCodeClickEvent(
                                order.id.toString(),
                                context = context
                            )
                        )
                    })
                }
            }

            if (viewModel.state.isLoading) {
                LoadingDialog {

                }
            }

            if (showCalenderDialog.value) {
                MyDatePickerDialog(onDateSelected = {
                    viewModel.onEvent(
                        OrderEvent.OnSearchEvent(
                            changeDateFormat(
                                it,
                                "yyyy-MM-dd",
                                "dd-MM-yyyy"
                            ) ?: ""
                        )
                    )
                }, openDialog = showCalenderDialog)
            }
        }
    }
}

@Composable
fun OrderItemCompose(item: Order, onOrderNoClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.r())
            .shadow(
                elevation = 4.r(),
                spotColor = Color.LightGray,
                shape = RoundedCornerShape(10.r())
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.r())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.orderNo,
                    style = bodyRegularTextStyle.copy(
                        color = APP_DEFAULT_COLOR,
                        fontWeight = FontWeight.W500,
                        fontSize = 14.ssp(),
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        onOrderNoClick()
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(color = ColorTextSecondary, shape = RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = item.orderStatus.uppercase(),
                        modifier = Modifier
                            .padding(horizontal = 10.r())
                            .padding(vertical = 2.r()),
                        style = bodyBoldTextStyle.copy(color = Color.White, fontSize = 14.ssp())
                    )
                }
            }


            Spacer(modifier = Modifier.height(5.r()))



            Text(
                text = item.outletName, style = bodyBoldTextStyle.copy(
                    color = Color.Black,
                )
            )
            Spacer(modifier = Modifier.height(5.r()))

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Amount: ${item.orderAmount}",
                    style = bodyRegularTextStyle.copy(color = Color.Black)
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.orderDate, style = bodyRegularTextStyle)
            }

        }
    }

}


@Composable
@Preview
fun PreviewOrderScreen() {
    OrderScreen(onBack = {})
}
