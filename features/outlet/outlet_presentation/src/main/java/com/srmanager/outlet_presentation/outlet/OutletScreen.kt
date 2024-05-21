package com.srmanager.outlet_presentation.outlet

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.bodyRegularTextStyle
import com.srmanager.core.designsystem.theme.smallBodyTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import com.srmanager.core.network.dto.Outlet
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OutletScreen(
    onBack: () -> Unit,
    viewModel: OutletViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onItemClick: (response: Outlet) -> Unit,
    onLocationClick: (response: Outlet) -> Unit,
    onCheckOutClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 10.r())
            ) {
                Spacer(modifier = Modifier.padding(top = 10.r()))
                TextField(singleLine = true,
                    value = viewModel.state.searchKey,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()

                        defaultKeyboardAction(ImeAction.Done)
                    }),
                    onValueChange = {
                        viewModel.onEvent(OutletEvent.OnSearchEvent(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        //.height(54.dp)
                        .clip(RoundedCornerShape(10.r()))
                        .border(
                            width = 1.r(),
                            shape = RoundedCornerShape(10.r()),
                            color = Color.LightGray
                        ),

                    placeholder = {
                        Text(
                            text = stringResource(id = CommonR.string.search),
                            style = bodyRegularTextStyle.copy(
                                color = ColorTextFieldPlaceholder,
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "")
                    })
                Spacer(modifier = Modifier.height(5.r()))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = CommonR.string.refresh),
                        style = smallBodyTextStyle.copy(fontWeight = FontWeight.W500),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(OutletEvent.OnRefreshEvent)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(5.r()))
                Box(
                    modifier = Modifier
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
                            items(viewModel.state.outletList.size) { index ->
                                Spacer(modifier = Modifier.height(10.r()))
                                ItemCompose(
                                    viewModel.state.outletList[index], index,
                                    onItemClick = { data ->
                                        onItemClick(data)
                                    },
                                    onLocationClick = { data ->
                                        onLocationClick(data)
                                    },
                                    onCheckOutClick = {
                                        onCheckOutClick()
                                    }
                                )
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
    response: Outlet,
    index: Int,
    onItemClick: (response: Outlet) -> Unit,
    onLocationClick: (response: Outlet) -> Unit,
    onCheckOutClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(response)
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
                                color = Color.DarkGray,
                                textAlign = TextAlign.Start
                            ),
                            modifier = Modifier.width(300.r()),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier
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
                                    onLocationClick(response)
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
