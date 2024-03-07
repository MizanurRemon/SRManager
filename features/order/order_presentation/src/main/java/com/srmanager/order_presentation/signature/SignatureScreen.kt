package com.srmanager.order_presentation.signature

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.base64ToImage
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.SignatureDialog
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyXSBoldTextStyle
import com.srmanager.core.designsystem.theme.bodyXSRegularTextStyle
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
    onEvent: (SignatureEvent) -> Unit
) {

    val isCustomerDialogOpen = remember { mutableStateOf(false) }
    val isSrDialogOpen = remember {
        mutableStateOf(false)
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

            InfoItem(title = CommonR.string.date, value = state.orderDate)

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .height(1.r())
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.r())) {
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

            Box(
                modifier = Modifier
                    .height(100.r())
                    .width(150.r())
                    .border(
                        width = 1.r(),
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.r())
                    )
                    .background(color = Color.White)
                    .clickable {
                        isCustomerDialogOpen.value = true
                    }
                    .align(alignment = Alignment.End)
            ) {

                if (state.customerSign.isEmpty()) {
                    Text(
                        text = stringResource(id = CommonR.string.sign_here),
                        modifier = Modifier.align(
                            Alignment.Center
                        ),
                        style = bodyBoldTextStyle
                    )
                } else {

                    Image(
                        bitmap = base64ToImage(state.customerSign).asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                    )

                }


            }

            Spacer(modifier = Modifier.height(10.r()))

            /*  Text(
                  text = stringResource(id = CommonR.string.sr_signature),
                  style = subHeading1TextStyle
              )
              Box(
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(200.r())
                      .padding(20.r())
                      .border(
                          width = 1.r(),
                          color = Color.LightGray,
                          shape = RoundedCornerShape(10.r())
                      )
                      .background(color = Color.White)
                      .clickable {
                          isSrDialogOpen.value = true
                      }
              ) {

                  if (state.srSign.isEmpty()) {

                      Text(
                          text = stringResource(id = CommonR.string.tap_here_to_sign),
                          modifier = Modifier.align(
                              Alignment.Center
                          ),
                          style = bodyBoldTextStyle
                      )
                  } else {

                      Image(
                          bitmap = base64ToImage(state.srSign).asImageBitmap(),
                          contentDescription = "",
                          modifier = Modifier
                              .align(Alignment.Center)
                              .fillMaxHeight()
                      )

                  }


              }
  */

            AppActionButtonCompose(
                stringId = CommonR.string.done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.r())
            ) {

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


    if (isSrDialogOpen.value) {
        SignatureDialog(
            isDialogOpen = isSrDialogOpen,
            onSave = {
                onEvent(SignatureEvent.OnSRSignEvent(it))
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
    ), uiEvent = flow { }, onEvent = {})
}



