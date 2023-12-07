package com.srmanager.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.text.font.FontStyle
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
fun AppHomeToolbarCompose(onClick: () -> Unit, icon: Int, title: Int, address: String) {
    Box(
        //shadowElevation = 9.r(),
        modifier = Modifier
            .background(color = APP_DEFAULT_COLOR)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.r())
        ) {

            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.r())
                    .clickable {
                        onClick()
                    }
                    .size(30.r())
            )

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    text = stringResource(id = title),
                    style = boldBodyTextStyle.copy(color = Color.White, fontSize = 16.sp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                onClick()
                            }
                            .size(16.r())
                    )
                    Spacer(modifier = Modifier.height(30.r()))

                    Text(
                        text = address,
                        style = boldBodyTextStyle.copy(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewHomeToolbarCompose() {
    AppToolbarCompose(onClick = {}, icon = DesignSystemR.drawable.ic_back, title = CommonR.string.back)
}

@Composable
@Preview
fun PreviewAppHomeToolBarCompose(){
    AppHomeToolbarCompose(onClick = { /*TODO*/ }, icon = DesignSystemR.drawable.ic_menu, title = CommonR.string.home, address = "Dhaka, Bangladesh")
}