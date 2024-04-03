package com.srmanager.order_presentation.signature

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.base64ToImage
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.components.SignatureDialog
import com.srmanager.core.designsystem.components.WarningDialogCompose
import com.srmanager.core.designsystem.generatePDF
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.bodyXSBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyXSRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.network.dto.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignatureScreen(
    onBack: () -> Unit,
    state: SignatureState,
    uiEvent: Flow<UiEvent>,
    onEvent: (SignatureEvent) -> Unit,
    onSuccess: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {


    val isCustomerDialogOpen = remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    onSuccess()
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



    Scaffold(topBar = {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.back
        )
    }, containerColor = Color.White) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.r())
            //.verticalScroll(rememberScrollState())
        ) {
            InfoItem(title = CommonR.string.outlet_id, value = state.outletID.toString())

            InfoItem(title = CommonR.string.contact, value = state.contact)

            InfoItem(title = CommonR.string.date, value = state.orderDate)

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .height(1.r())
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.r())
            ) {
                Text(
                    text = stringResource(id = CommonR.string.product_id),
                    modifier = Modifier.width(120.r()),
                    style = bodyXSBoldTextStyle.copy(textAlign = TextAlign.Start)
                )

                Text(
                    text = stringResource(id = CommonR.string.quantity),
                    modifier = Modifier.width(100.r()),
                    style = bodyXSBoldTextStyle.copy(textAlign = TextAlign.Center)
                )

                Text(
                    text = stringResource(id = CommonR.string.mrp_price),
                    modifier = Modifier.width(100.r()),
                    style = bodyXSBoldTextStyle.copy(textAlign = TextAlign.Center)
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = CommonR.string.total),
                    modifier = Modifier.width(100.r()),
                    style = bodyXSBoldTextStyle.copy(textAlign = TextAlign.End)
                )
            }

            state.productsList.forEach { product ->
                ProductItemCompose(product)
            }

            InfoItem(title = CommonR.string.total, value = state.total.toString())


            Spacer(modifier = Modifier.height(20.r()))

            Column(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .width(150.r()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(100.r())
                        .fillMaxWidth()
                        .background(color = Color(0xFFF5F5F5))
                        .clickable {
                            isCustomerDialogOpen.value = true
                        }

                ) {

                    if (state.customerSign.isNotEmpty()) {
                        Image(
                            bitmap = base64ToImage(state.customerSign).asImageBitmap(),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxHeight()
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(1.r())
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                )

                Text(
                    text = stringResource(id = CommonR.string.sign_here),
                    style = bodyXSBoldTextStyle,
                    modifier = Modifier.padding(top = 5.r())
                )
            }

            Spacer(modifier = Modifier.height(10.r()))

            AppActionButtonCompose(
                stringId = CommonR.string.done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.r())
            ) {
                onEvent(SignatureEvent.OnDoneEvent)
            }

        }
    }

    if (isCustomerDialogOpen.value) {
        SignatureDialog(
            isDialogOpen = isCustomerDialogOpen,
            onSave = {
                onEvent(SignatureEvent.OnCustomerSignEvent(it))
            }
        )
    }

    if (state.isLoading) {
        LoadingDialog {

        }
    }

    if (state.orderSuccessDialog) {
        WarningDialogCompose(
            message = CommonR.string.success,
            buttonText = CommonR.string.done,
            image = DesignSystemR.drawable.ic_success,
            isDialogOpen = remember {
                mutableStateOf(true)
            },
            onClick = {
                onSuccess()

                generatePDF(context, state.outletID, state.orderDate, state.contact, state.productsList, state.total)
            }
        )
    }

}

@Composable
fun InfoItem(@StringRes title: Int, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.r()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = title), style = bodyXSBoldTextStyle)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value,
            modifier = Modifier.width(100.r()),
            style = bodyXSRegularTextStyle.copy(textAlign = TextAlign.End)
        )
    }
}

@Composable
fun ProductItemCompose(product: Product) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.r())
        ) {
            Text(
                text = product.id.toString(),
                modifier = Modifier.width(120.r()),
                style = bodyXSRegularTextStyle.copy(textAlign = TextAlign.Start)
            )

            Text(
                text = product.selectedItemCount.toString(),
                modifier = Modifier.width(100.r()),
                style = bodyXSRegularTextStyle.copy(textAlign = TextAlign.Center)
            )

            Text(
                text = product.mrpPrice.toString(),
                modifier = Modifier.width(100.r()),
                style = bodyXSRegularTextStyle.copy(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = product.selectedItemTotalPrice.toString(),
                modifier = Modifier.width(100.r()),
                style = bodyXSRegularTextStyle.copy(textAlign = TextAlign.End)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .height(1.r())
        )
    }


}


@Composable
fun PdfContentCompose() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(10.r())
            .fillMaxWidth()
    ) {
        Text(text = "PDF", style = subHeading1TextStyle)
    }
}

@Composable
@Preview
fun PreviewPdfContentCompose() {
    PdfContentCompose()
}

@Composable
@Preview
fun PreviewSignatureScreen() {
    SignatureScreen(onBack = { }, state = SignatureState(
        productsList = listOf(
            Product(
                title = "Banana",
                id = 5,
                mrpPrice = 2.5,
                wholeSalePrice = 2.0,
                lastPurchasePrice = 1.5,
                vatPercentage = 1.2,
                price = 3.0,
                availableQuantity = 5.0,
                selectedItemCount = 1,
                selectedItemTotalPrice = 5.5
            ),
            Product(
                title = "Banana",
                id = 5,
                mrpPrice = 2.5,
                wholeSalePrice = 2.0,
                lastPurchasePrice = 1.5,
                vatPercentage = 1.2,
                price = 3.0,
                availableQuantity = 5.0,
                selectedItemCount = 1,
                selectedItemTotalPrice = 5.5
            )
        )
    ),
        uiEvent = flow { },
        onEvent = {},
        snackbarHostState = remember {
            SnackbarHostState()
        },
        onSuccess = {}
    )
}


