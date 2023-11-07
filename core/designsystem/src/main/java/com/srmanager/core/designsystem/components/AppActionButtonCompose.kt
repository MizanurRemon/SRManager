package com.srmanager.core.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_BUTTON_COLOR
import com.srmanager.core.designsystem.theme.ColorPrimaryDark
import com.srmanager.core.designsystem.theme.DISABLED_COLOR
import com.srmanager.core.designsystem.theme.conditional
import com.srmanager.core.designsystem.theme.fontRoboto


@Composable
fun AppActionButtonCompose(
    stringId: Int,
    modifier: Modifier = Modifier,
    bgColor: Color = APP_DEFAULT_BUTTON_COLOR,
    enable: Boolean = true,
    textColor: Color = Color.White,
    borderColor: Color? = null,
    onActionButtonClick: () -> Unit
) {


    val cornerRadius = 37.dp
    val borderWidth = 1.dp
    return Button(
        onClick = {
            if (enable)
                onActionButtonClick()
        },
        enabled = enable,
        colors = ButtonDefaults.buttonColors(containerColor = bgColor, disabledContainerColor = DISABLED_COLOR),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(cornerRadius))
           // .background(color = bgColor)
            .conditional(borderColor != null) {
                return@conditional border(
                    borderWidth,
                    ColorPrimaryDark,
                    shape = RoundedCornerShape(cornerRadius),
                )
            }
    ) {
        Text(
            text = stringResource(id = stringId),
            style = TextStyle(
                fontFamily = fontRoboto,
                fontSize = 16.ssp(),
                color = textColor,
                fontWeight = FontWeight.W500,
                lineHeight = 28.ssp(),
            ),
            maxLines = 1,
            textAlign = TextAlign.Center,
        )

    }
}


@Preview
@Composable
fun PreviewAppActionButtonCompose() {
    AppActionButtonCompose(
        bgColor = ColorPrimaryDark,
        enable = true,
        textColor = Color.White,
        borderColor = null,
        stringId = CommonR.string.continues
    ) {}
}
