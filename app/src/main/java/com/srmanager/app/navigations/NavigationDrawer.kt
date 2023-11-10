package com.srmanager.app.navigations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.srmanager.core.common.R as CommonR

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

        HeaderComposer(onCloseClick = {
            onCloseClick()
        })
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
                            .size(25.r()),
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
fun HeaderComposer(onCloseClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = APP_DEFAULT_COLOR)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.r()), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding())

            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.clickable {
                    onCloseClick()
                })
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.r()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = DesignSystemR.drawable.app_icon),
                modifier = Modifier
                    .height(120.dp)
                    /*.border(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, color = Color.LightGray)
                    )*/
                    .shadow(elevation = 10.dp,shape = RoundedCornerShape(15.dp), ),
                contentDescription = ""
            )

            Text(
                text = stringResource(id = CommonR.string.app_name),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 10.dp)
            )

        }


    }
}

@Composable
@Preview
fun PreviewHeaderComposer() {

    HeaderComposer {

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




