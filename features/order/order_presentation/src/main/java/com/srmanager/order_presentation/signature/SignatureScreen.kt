package com.srmanager.order_presentation.signature

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import com.srmanager.core.designsystem.R
import com.srmanager.core.designsystem.components.AppToolbarCompose
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.base64ToImage
import com.srmanager.core.designsystem.PathState
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.AppCancelButtonCompose
import com.srmanager.core.designsystem.theme.SignatureDialog
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignatureScreen(onBack: () -> Unit, viewModel: SignatureViewModel = hiltViewModel()) {

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
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.r())
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = CommonR.string.customer_sign),
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
                        isCustomerDialogOpen.value = true
                    }
            ) {

                if (viewModel.state.customerSign.isEmpty()) {
                    Text(
                        text = stringResource(id = CommonR.string.tap_here_to_sign),
                        modifier = Modifier.align(
                            Alignment.Center
                        ),
                        style = bodyBoldTextStyle
                    )
                } else {

                    Image(
                        bitmap = base64ToImage(viewModel.state.customerSign).asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                    )

                }


            }

            Spacer(modifier = Modifier.height(10.r()))

            Text(
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

                if (viewModel.state.srSign.isEmpty()) {

                    Text(
                        text = stringResource(id = CommonR.string.tap_here_to_sign),
                        modifier = Modifier.align(
                            Alignment.Center
                        ),
                        style = bodyBoldTextStyle
                    )
                } else {

                    Image(
                        bitmap = base64ToImage(viewModel.state.srSign).asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                    )

                }


            }


            AppActionButtonCompose(
                stringId = CommonR.string.done,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.r())
            ) {

            }

        }
    }

    if (isCustomerDialogOpen.value) {
        SignatureDialog(
            isDialogOpen = isCustomerDialogOpen,
            onSave = {
                viewModel.onEvent(SignatureEvent.OnCustomerSignEvent(it))
            }
        )
    }


    if (isSrDialogOpen.value) {
        SignatureDialog(
            isDialogOpen = isSrDialogOpen,
            onSave = {
                viewModel.onEvent(SignatureEvent.OnSRSignEvent(it))
            }
        )
    }


}



