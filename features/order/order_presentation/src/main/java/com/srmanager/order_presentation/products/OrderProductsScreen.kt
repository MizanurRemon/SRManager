package com.srmanager.order_presentation.products

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.ImageComposer
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.order_domain.model.Products

@Composable
fun OrderProductsScreen(
    onBack: () -> Unit,
    viewModel: ProductsViewModel = hiltViewModel(),
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
                .padding(bottom = 20.r(), top = 10.r()),
            enable = viewModel.state.isNextButtonEnabled
        ) {
            onNextClick()
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {

                if (viewModel.state.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = APP_DEFAULT_COLOR,
                        modifier = Modifier
                            .size(20.r())
                            .padding(top = 10.r())
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 20.r())
                    ) {
                        viewModel.state.productsList.forEach { product ->
                            Spacer(modifier = Modifier.height(10.r()))

                            ItemCompose(
                                product = product,
                                onItemClick = {
                                    viewModel.onEvent(
                                        OrderProductsEvent.OnItemClickEvent(
                                            product.id,
                                            !product.isSelected
                                        )
                                    )
                                },
                                onIncrementClick = {
                                    viewModel.onEvent(OrderProductsEvent.OnIncrementEvent(product.id))
                                },
                                onDecrementClick = {
                                    viewModel.onEvent(OrderProductsEvent.OnDecrementEvent(product.id))
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun ItemCompose(
    product: Products,
    onItemClick: () -> Unit,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.r())
            .padding(vertical = 2.r())
            .shadow(elevation = 5.r(), shape = RoundedCornerShape(16.r()), spotColor = Color.Gray)
            .clickable {
                onItemClick()
            },
        shape = RoundedCornerShape(16.r()),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        val constraints = ConstraintSet {
            val image = createRefFor("image")
            val columnContent = createRefFor("columnContent")
            val checkBox = createRefFor("checkBox")

            constrain(image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }

            constrain(columnContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(image.start)
                end.linkTo(checkBox.end)
                width = Dimension.fillToConstraints
            }

            constrain(checkBox) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, margin = 10.dp)
            }

            createHorizontalChain(
                image,
                columnContent,
                checkBox,
                chainStyle = ChainStyle.SpreadInside
            )
        }

        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.r()),
        ) {
            ImageComposer(
                imagePath = product.image, modifier = Modifier
                    .size(100.r())
                    .padding(20.r())
                    .layoutId("image")
            )

            Column(
                modifier = Modifier
                    .padding(10.r())
                    .fillMaxWidth()
                    .layoutId("columnContent")
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Stock: ${product.stock} ${product.unit}",
                        style = bodyRegularTextStyle.copy(fontSize = 14.ssp(), color = Color.Black)
                    )

                    Spacer(
                        modifier = Modifier
                            .width(10.r())
                    )

                    Spacer(
                        modifier = Modifier
                            .width(1.r())
                            .height(14.r())
                            .background(color = Color.Black)
                    )


                    Spacer(
                        modifier = Modifier
                            .width(10.r())
                    )

                    Text(
                        text = "Price: " + product.price,
                        style = bodyRegularTextStyle.copy(fontSize = 14.ssp(), color = Color.Black)
                    )
                }

                Spacer(modifier = Modifier.height(10.r()))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = {

                        if (product.selectedItemCount < product.stock) {
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
                        text = "${product.selectedItemCount} / ${product.stock}",
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


            Box(
                modifier = Modifier
                    .border(
                        width = 1.r(),
                        color = APP_DEFAULT_COLOR,
                        shape = CircleShape
                    )
                    .background(
                        color = if (product.isSelected) APP_DEFAULT_COLOR else Color.White,
                        shape = CircleShape
                    )
                    .layoutId("checkBox")
                    .size(20.r()),

                ) {
                if (product.isSelected) {
                    Icon(
                        painter = painterResource(id = DesignSystemR.drawable.ic_check),
                        contentDescription = null,
                        modifier = Modifier.padding(3.r()),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewItemCompose() {

    ItemCompose(
        product = Products(
            id = 1,
            title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
            stock = 25,
            price = 75.20,
            unit = "piece",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
        ), onItemClick = {}, onIncrementClick = {}, onDecrementClick = {}
    )
}
