package com.srmanager.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.bodyXSRegularTextStyle
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun WarningDialogCompose(
    @StringRes message: Int,
    @StringRes buttonText: Int,
    @DrawableRes image: Int,
    isDialogOpen: MutableState<Boolean>,
    onClick: ()-> Unit
) {
    if (isDialogOpen.value) {
        Dialog(
            onDismissRequest = { isDialogOpen.value = true },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.r())
                    .background(color = Color.White, shape = RoundedCornerShape(20.r()))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(20.r()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier.size(60.r())
                    )

                    Spacer(modifier = Modifier.height(10.r()))

                    Text(
                        text = stringResource(id = message),
                        style = bodyXSRegularTextStyle.copy(textAlign = TextAlign.Center)
                    )

                    Spacer(modifier = Modifier.height(10.r()))

                    AppActionButtonCompose(
                        stringId = buttonText
                    ) {
                        isDialogOpen.value = false
                        onClick()
                    }
                }
            }
        }
    }


}

@Composable
@Preview
fun PreviewWarningDialogCompose() {
    val isDialogOpen = remember { mutableStateOf(true) }

    WarningDialogCompose(
        message = CommonR.string.lorem_ipsum,
        buttonText = CommonR.string.done,
        image = DesignSystemR.drawable.ic_success,
        isDialogOpen = isDialogOpen,
        onClick = {}
    )
}

