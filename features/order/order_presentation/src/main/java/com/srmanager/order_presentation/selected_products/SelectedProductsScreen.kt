package com.srmanager.order_presentation.selected_products

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.r())
            ) {
                state.productsList.forEach { product ->
                    Spacer(modifier = Modifier.height(10.r()))

                    ItemCompose(
                        product = product,

                        onIncrementClick = {
                            onEvent(SelectedProductEvent.OnIncrementEvent(product.id))
                        },
                        onDecrementClick = {
                            onEvent(SelectedProductEvent.OnDecrementEvent(product.id))
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
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit
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
            .padding(horizontal = 15.r())
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
                            onIncrementClick()
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
                            onDecrementClick()
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