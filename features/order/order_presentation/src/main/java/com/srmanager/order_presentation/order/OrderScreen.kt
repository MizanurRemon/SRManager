package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextSecondary
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.network.dto.Order
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    onBack: () -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {

    val context = LocalContext.current

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.r())
                    .verticalScroll(rememberScrollState()),
            ) {
                viewModel.state.orderList.forEach { response ->
                    OrderItemCompose(item = response, onOrderNoClick = {
                        viewModel.onEvent(
                            OrderEvent.OnOrderCodeClickEvent(
                                response.id.toString(),
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
        }
    }
}

@Composable
fun OrderItemCompose(item: Order, onOrderNoClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.r())
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
