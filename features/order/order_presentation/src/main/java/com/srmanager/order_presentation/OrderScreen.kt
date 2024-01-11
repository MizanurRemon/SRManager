package com.srmanager.order_presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR_LIGHT
import com.srmanager.core.designsystem.theme.ColorTextSecondary
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.order_domain.model.OrderItem
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(onBack: () -> Unit, viewModel: OrderViewModel = hiltViewModel()) {
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = APP_DEFAULT_COLOR_LIGHT,
                        shape = RoundedCornerShape(bottomEnd = 20.r(), bottomStart = 20.r())
                    )
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.r())
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = CommonR.string.start),
                        style = bodyBoldTextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.width(10.r()))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = APP_DEFAULT_COLOR)
                    ) {
                        Text(text = viewModel.state.startDate)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(id = CommonR.string.end),
                        style = bodyBoldTextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.width(10.r()))

                    Button(
                        onClick = { }, colors = ButtonDefaults.buttonColors(
                            containerColor = APP_DEFAULT_COLOR
                        )
                    ) {
                        Text(text = viewModel.state.endDate)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                ORDERS.forEach { response ->
                    OrderItemCompose(item = response)
                }
            }
        }
    }
}

@Composable
fun OrderItemCompose(item: OrderItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.r())
            .padding(vertical = 5.r()),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.r()
        ),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.r())
        ) {

            Row {
                Text(
                    text = item.orderCode, style = bodyRegularTextStyle.copy(
                        color = Color.Black, fontWeight = FontWeight.W500, fontSize = 14.ssp()
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(color = ColorTextSecondary, shape = RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = item.status.uppercase(),
                        modifier = Modifier
                            .padding(horizontal = 10.r())
                            .padding(vertical = 2.r()),
                        style = bodyBoldTextStyle.copy(color = Color.White, fontSize = 14.ssp())
                    )
                }
            }


            Spacer(modifier = Modifier.height(5.r()))



            Text(
                text = item.name, style = bodyBoldTextStyle.copy(
                    color = Color.Black,
                )
            )
            Spacer(modifier = Modifier.height(5.r()))

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Amount: ${item.amount}",
                    style = bodyRegularTextStyle.copy(color = Color.Black)
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.date, style = bodyRegularTextStyle)
            }

        }
    }

}

@Composable
@Preview
fun PreviewOrderItem() {
    val item = ORDERS.first()
    OrderItemCompose(item)
}

/*
@Composable
@Preview
fun PreviewOrderScreen() {
    //OrderScreen (onBack = {})
}*/
