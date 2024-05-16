package com.srmanager.order_presentation.selected_products

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.network.dto.Product
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun SelectedProductsScreen(
    onBack: () -> Unit,
    state: SelectedProductsState,
    onEvent: (SelectedProductEvent) -> Unit,
    onNextClick: () -> Unit
) {
    Scaffold(topBar = {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.back
        )
    }, bottomBar = {
        AppActionButtonCompose(
            stringId = CommonR.string.next,
            modifier = Modifier
                .padding(horizontal = 40.r())
                .padding(bottom = 30.r(), top = 10.r()),
        ) {
            onNextClick()
        }
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 15.r())
        ) {

            Row(modifier = Modifier.padding(vertical = 10.r())) {
                Text(text = stringResource(id = CommonR.string.total), style = bodyRegularTextStyle)

                Spacer(modifier = Modifier.weight(1f))

                Text(text = state.totalAmount.toString(), style = bodyBoldTextStyle)

            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .height(1.r())
                    .padding(bottom = 10.r())
            )

            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .padding(bottom = 20.r())
            ) {
                items(state.productsList) { product ->
                    Spacer(modifier = Modifier.height(10.r()))

                    ItemCompose(
                        product = product,

                        onIncrementClick = { itemCount ->
                            onEvent(
                                SelectedProductEvent.OnIncrementEvent(
                                    product.id,
                                    itemCount
                                )
                            )
                        },
                        onDecrementClick = { itemCount ->
                            onEvent(
                                SelectedProductEvent.OnDecrementEvent(
                                    product.id,
                                    itemCount
                                )
                            )
                        }
                    )
                }
            }
        }
    })
}

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun ItemCompose(
    product: Product,
    onIncrementClick: (itemCount: Int) -> Unit,
    onDecrementClick: (itemCount: Int) -> Unit
) {


    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.W300)) {
            append(stringResource(id = CommonR.string.stock) + ": ")
        }


        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.W700,
            )
        ) {
            append("${product.availableQuantity}")
        }

        withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.W300)) {
            append(" | " + stringResource(id = CommonR.string.price) + ": ")
        }


        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.W700,
            )
        ) {
            append("${product.price}")
        }


        withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.W300)) {
            append(" | " + stringResource(id = CommonR.string.mrp_price) + ": ")
        }


        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.W700,
            )
        ) {
            append("${product.mrpPrice}")
        }

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.r())
            .shadow(elevation = 5.r(), shape = RoundedCornerShape(16.r()), spotColor = Color.Gray),
        shape = RoundedCornerShape(16.r()),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.r(), start = 10.r()),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(20.r()))

            Column(
                modifier = Modifier
                    .padding(10.r())
                    .fillMaxWidth()
            ) {
                Text(
                    text = product.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = subHeading1TextStyle.copy(
                        color = Color.Black,
                        fontSize = 15.ssp(),
                        textAlign = TextAlign.Start
                    )
                )

                Spacer(modifier = Modifier.height(5.r()))


                Text(
                    text = annotatedText,
                    style = bodyRegularTextStyle.copy(
                        fontSize = 14.ssp(),
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(10.r()))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = {

                        if (product.selectedItemCount < product.availableQuantity) {
                            onIncrementClick(product.selectedItemCount)
                        }

                    }, modifier = Modifier.size(20.r())) {
                        Box(
                            modifier = Modifier.border(
                                width = 1.r(),
                                color = Color.Black,
                                shape = RoundedCornerShape(16.r())
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = DesignSystemR.drawable.ic_add),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.r()))

                    Text(
                        text = "${product.selectedItemCount} / ${product.availableQuantity}",
                        style = bodyRegularTextStyle.copy(color = Color.Black, fontSize = 14.ssp())
                    )

                    Spacer(modifier = Modifier.width(20.r()))

                    IconButton(onClick = {
                        if (product.selectedItemCount > 1) {
                            onDecrementClick(product.selectedItemCount)
                        }
                    }, modifier = Modifier.size(20.r())) {
                        Box(
                            modifier = Modifier.border(
                                width = 1.r(),
                                color = Color.Black,
                                shape = RoundedCornerShape(16.r())
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = DesignSystemR.drawable.ic_remove),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSelectedProductsScreen() {
    SelectedProductsScreen(onBack = {

    }, state = SelectedProductsState(), onEvent = { }, onNextClick = {})
}