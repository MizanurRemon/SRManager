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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.network.dto.Product
import kotlinx.coroutines.flow.Flow
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OrderProductsScreen(
    onBack: () -> Unit,
    onNextClick: () -> Unit,
    state: ProductsState,
    uiEvent: Flow<UiEvent>,
    onEvent: (ProductsEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
            enable = state.isNextButtonEnabled
        ) {
            onNextClick()
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.r())
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(10.r()))

            TextField(
                singleLine = true,
                value = state.searchKey,
                onValueChange = {
                    onEvent(ProductsEvent.OnSearchEvent(it))
                },
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
                        text = stringResource(id = CommonR.string.search_products),
                        style = bodyRegularTextStyle.copy(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.LightGray)
                }
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {

                if (state.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = APP_DEFAULT_COLOR,
                        modifier = Modifier
                            .size(20.r())
                            .padding(top = 10.r())
                    )
                } else {
                    val lazyColumnListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyColumnListState
                    ) {
                        items(state.productsList) { product ->
                            Spacer(modifier = Modifier.height(10.r()))
                            ItemCompose(
                                product = product,
                                onItemClick = {
                                    onEvent(
                                        ProductsEvent.OnItemClickEvent(
                                            product.id,
                                            !product.isSelected
                                        )
                                    )
                                },
                                onIncrementClick = { itemCount ->
                                    onEvent(
                                        ProductsEvent.OnIncrementEvent(
                                            product.id,
                                            itemCount
                                        )
                                    )
                                },
                                onDecrementClick = { itemCount ->
                                    onEvent(
                                        ProductsEvent.OnDecrementEvent(
                                            product.id,
                                            itemCount
                                        )
                                    )
                                },
                                keyboardController = keyboardController,
                                state = state,
                                onEvent = onEvent,
                            )
                        }

                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun ItemCompose(
    product: Product,
    onItemClick: () -> Unit,
    onIncrementClick: (itemCount: Int) -> Unit,
    onDecrementClick: (itemCount: Int) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    state: ProductsState,
    onEvent: (ProductsEvent) -> Unit
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

    val qty = remember {
        mutableStateOf("1")
    }

    qty.value = product.selectedItemCount.toString()

    val isShowCheck = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.r(), start = 10.r()),
            verticalAlignment = Alignment.CenterVertically
        ) {
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

                    TextField(
                        value = qty.value,
                        onValueChange = { newText ->
                            val filteredText = newText.filter { it.isDigit() } // Allow only digits

                            qty.value = filteredText


                            isShowCheck.value = true
                        },
                        textStyle = bodyRegularTextStyle.copy(
                            color = Color.Black,
                            fontSize = 14.ssp(),
                            textAlign = TextAlign.End
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()

                            defaultKeyboardAction(ImeAction.Done)
                        }),
                        modifier = Modifier
                            .width(100.r())
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(10.r()))
                            .border(
                                width = 1.r(),
                                shape = RoundedCornerShape(10.r()),
                                color = Color.LightGray
                            ),

                        leadingIcon = {
                            if (isShowCheck.value) {
                                Icon(
                                    painter = painterResource(id = DesignSystemR.drawable.ic_check),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.r())
                                        .clickable {
                                            when {
                                                qty.value.isNotEmpty() && (qty.value.toDouble() < product.availableQuantity) -> {
                                                    onEvent(
                                                        ProductsEvent.OnQuantityInput(
                                                            id = product.id,
                                                            qty = qty.value.toDouble(),
                                                            isSelected = true
                                                        )
                                                    )
                                                }

                                                qty.value.isNotEmpty() && (qty.value.toDouble() > product.availableQuantity) -> {
                                                    onEvent(
                                                        ProductsEvent.OnQuantityInput(
                                                            id = product.id,
                                                            qty = product.availableQuantity,
                                                            isSelected = true
                                                        )
                                                    )
                                                }
                                            }

                                            isShowCheck.value = false
                                        },
                                    tint = APP_DEFAULT_COLOR
                                )
                            }
                        }

                    )

                    Text(
                        text = " / ${product.availableQuantity}",
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

@Composable
@Preview
fun PreviewItemCompose() {

}
