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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.srmanager.core.common.util.capitalizeFirstCharacter
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.AppBrush
import com.srmanager.core.designsystem.theme.DRAWER_SELECTED
import com.srmanager.core.designsystem.R as DesignSystemR

@Composable
fun NavigationDrawer(
    currentDestination: NavDestination?,
    onCloseClick: () -> Unit,
    onNavigationDrawerItemClick: (NavigationItem) -> Unit
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
                        .padding(start = 5.r())
                        .clickable {
                            onNavigationDrawerItemClick(item)
                        }
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) DRAWER_SELECTED else Color.Transparent
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(20.r()),
                    )
                    Text(
                        text = stringResource(id = item.title).capitalizeFirstCharacter(),
                        style = TextStyle(color = Color.Black)
                    )
                }
            }

        }
    }
}

@Composable
@Preview
fun PreviewNavigationDrawer() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationDrawer(
        currentDestination = currentDestination,
        onCloseClick = { },
        onNavigationDrawerItemClick = {}
    )
}


