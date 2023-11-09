package com.srmanager.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyXSBoldTextStyle
import com.srmanager.core.designsystem.theme.boldBodyTextStyle
import com.srmanager.core.designsystem.w
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbarCompose(onClick: () -> Unit, icon : Int, title : Int) {
    Surface(shadowElevation = 9.r()) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = title),
                    style = boldBodyTextStyle.copy(color = Color.White, fontSize = 16.sp)
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = APP_DEFAULT_COLOR
            ), navigationIcon = {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 10.r())
                        .clickable {
                            onClick()
                        }
                        .size(25.r())
                )
            }
        )
    }
}

@Composable
@Preview
fun PreviewHomeToolbarCompose() {
    AppToolbarCompose(onClick = {}, icon = DesignSystemR.drawable.ic_menu, title = CommonR.string.app_name)
}