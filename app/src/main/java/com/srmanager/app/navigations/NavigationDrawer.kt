package com.srmanager.app.navigations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.srmanager.core.common.util.NAVIGATION_ITEMS
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.DRAWER_SELECTED
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@Composable
fun NavigationDrawer(
    currentDestination: NavDestination?,
    onCloseClick: () -> Unit,
    onNavigationDrawerItemClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = AppBrush)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = APP_DEFAULT_COLOR)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.padding())
                IconButton(onClick = {
                    onCloseClick()
                }) {
                    Icon(imageVector = Icons.Outlined.Close, contentDescription = "")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
//                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Image(
                    painterResource(id = DesignSystemR.drawable.avatar),
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(120.dp),
                    contentDescription = ""
                )

                Text(
                    text = "Martin Joseph",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "Entrepreneur",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
            }


        }
        Column(modifier = Modifier.padding(5.dp)) {

            NAVIGATION_ITEMS.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .clickable {
                            onNavigationDrawerItemClick(item)
                        }
//                        .border(
//                            width = .5.dp,
//                            color = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) DRAWER_SELECTED_BORDER else Color.White
//                        )
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = if (currentDestination?.hierarchy?.any { it.route == item } == true) DRAWER_SELECTED else Color.White
                        )
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(15.dp),
                        style = TextStyle(color = Color.Black)
                    )
                }
            }

        }
    }
}


