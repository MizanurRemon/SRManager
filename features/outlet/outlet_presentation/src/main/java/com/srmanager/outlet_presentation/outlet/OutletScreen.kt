package com.srmanager.outlet_presentation.outlet

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.network.dto.Data
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OutletScreen(
    onBack: () -> Unit,
    viewModel: OutletViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onItemClick: () -> Unit,
    onLocationClick: () -> Unit,
    onCheckOutClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {


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

    Scaffold(
        topBar = {
            AppToolbarCompose(
                onClick = { onBack() },
                icon = DesignSystemR.drawable.ic_back,
                title = CommonR.string.back
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddClick()
                },
                modifier = Modifier.padding(end = 20.r(), bottom = 40.r()),
                containerColor = APP_DEFAULT_COLOR
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(30.r()),
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {

                if (viewModel.state.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = APP_DEFAULT_COLOR,
                        modifier = Modifier
                            .size(20.r())
                            .padding(top = 10.r())
                    )
                } else {
                    val lazyColumnListState = rememberLazyListState()
                    LazyColumn(
                        state = lazyColumnListState,
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        items(viewModel.state.outletList.data.size) { index ->
                            Spacer(modifier = Modifier.height(10.r()))
                            ItemCompose(
                                viewModel.state.outletList.data[index], index,
                                onItemClick = {
                                    onItemClick()
                                },
                                onLocationClick = {
                                    onLocationClick()
                                },
                            ) {
                                onCheckOutClick()
                            }
                        }
                    }
                }

            }
        }
    )

}

@Composable
fun ItemCompose(
    response: Data,
    index: Int,
    onItemClick: () -> Unit,
    onLocationClick: () -> Unit, onCheckOutClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.r())
            .clickable {
                onItemClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = if (index % 2 == 0) Color.Yellow else Color.Green)
        ) {
            Spacer(
                modifier = Modifier
                    .width(10.r())
                    .background(color = Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            // .background(color = Color.DarkGray)
                            .padding(15.r())
                    ) {
                        Text(
                            text = response.outletName,
                            style = subHeading1TextStyle.copy(color = Color.Black)
                        )

                        Text(
                            text = "Owner: ${response.ownerName}",
                            style = bodyRegularTextStyle.copy(
                                fontSize = 14.ssp(),
                                fontWeight = FontWeight.W500,
                                color = Color.DarkGray
                            )
                        )
                        Text(
                            text = "Address: ${response.address}",
                            style = bodyRegularTextStyle.copy(
                                fontSize = 14.ssp(),
                                fontWeight = FontWeight.W500,
                                color = Color.DarkGray
                            )
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier
                            //.fillMaxWidth()
                            .height(100.r()),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {


                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(color = Color(0xff606888))
                                .weight(1f)
                                .width(25.r())
                                .clickable {
                                    Toast
                                        .makeText(context, "call", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(16.r())
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(color = Color(0xFF23585F))
                                .weight(1f)
                                .width(25.r())
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = rememberRipple()
                                ) {
                                    onLocationClick()
                                }
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(16.r())
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(color = Color(0xFF673AB7))
                                .weight(1f)
                                .width(25.r())
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = rememberRipple()
                                ) {
                                    onCheckOutClick()
                                }
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(16.r())
                            )
                        }

                    }
                }

            }
        }
    }
}

/*@Composable
@Preview
fun previewItemCompose() {
    ItemCompose(
        response = OUTLET_LIST[0],
        index = 1,
        onItemClick = {},
        onLocationClick = {}
    ) {}
}*/

@Composable
@Preview
fun PreviewCustomerAddScreen() {
    OutletScreen(
        onBack = {},
        onAddClick = {},
        onItemClick = {},
        onLocationClick = {},
        onCheckOutClick = {},
        snackbarHostState = remember { SnackbarHostState() })
}
