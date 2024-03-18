package com.srmanager.outlet_presentation.outlet_details

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.common.util.*
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.*
import com.srmanager.core.network.dto.Outlet
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OutletDetailsScreen(
    onBack: () -> Unit,
    viewModel: OutletDetailsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    outletDetails: Outlet?
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val openDatePickerDialog = remember {
        mutableStateOf(false)
    }

    val openExpiryDatePickerDialog = remember {
        mutableStateOf(false)
    }

    val openImagePickerDialog = remember {
        mutableStateOf(false)
    }

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



    LaunchedEffect(key1 = true) {
        viewModel.getOutletDetails(outletID = outletDetails!!.id.toString())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppToolbarCompose(
            onClick = { onBack() },
            icon = DesignSystemR.drawable.ic_back,
            title = CommonR.string.back
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.r())
                .verticalScroll(rememberScrollState()),

            ) {

            Text(
                text = stringResource(id = CommonR.string.outlet_details),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            Text(
                text = stringResource(id = CommonR.string.outlet_name),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )
            TextField(value = viewModel.state.outletName,
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
                    viewModel.onEvent(OutletDetailsEvent.OnOutletNameEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isOutletNameError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_outlet_name),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                })


            Text(
                text = stringResource(id = CommonR.string.owner_name),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.ownerName,
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
                    viewModel.onEvent(OutletDetailsEvent.OnOwnerNameEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isOwnerNameError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_owner_name),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                })
            Text(
                text = stringResource(id = CommonR.string.email),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.email,
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
                    viewModel.onEvent(OutletDetailsEvent.OnEmailEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isEmailError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_email),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                })

            Text(
                text = stringResource(id = CommonR.string.market_name),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )


            ExposedDropdownMenuBox(
                expanded = viewModel.state.isMarketNameExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {


                TextField(
                    value = viewModel.state.marketName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (viewModel.state.isMarketNameExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(OutletDetailsEvent.OnMarketNameDropDownClick)
                            }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Color.LightGray
                        ),
                    placeholder = {
                        Text(
                            text = MARKET_NAMES[0],
                            style = TextStyle(
                                color = ColorTextFieldPlaceholder,
                            )
                        )
                    }
                )

                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(color = Color.White)
                        .exposedDropdownSize(),
                    expanded = viewModel.state.isMarketNameExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(OutletDetailsEvent.OnMarketNameDropDownClick)
                    }) {
                    Text(
                        text = stringResource(CommonR.string.select_market),
                        modifier = Modifier.padding(start = 20.r()),
                        style = subHeading1TextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().padding(top = 10.r()).background(color = Color.Black).height(1.r()))
                    viewModel.state.marketNameList.forEach { response ->
                        DropdownMenuItem(text = {
                            Text(
                                text = response.text.toString(),
                                color = Color.Black
                            )
                        }, onClick = {
                            viewModel.onEvent(OutletDetailsEvent.OnMarketNameSelection(response.text.toString()))
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    }
                }
            }

            Text(
                text = stringResource(id = CommonR.string.shop_ethnicity),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )


            ExposedDropdownMenuBox(
                expanded = viewModel.state.isEthnicityExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {

                TextField(
                    value = viewModel.state.ethnicity,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (viewModel.state.isEthnicityExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(OutletDetailsEvent.OnEthnicityDropDownClick)
                            }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Color.LightGray
                        ),
                    placeholder = {
                        Text(
                            text = ETHNICITIES[0],
                            style = TextStyle(
                                color = ColorTextFieldPlaceholder,
                            )
                        )
                    }
                )

                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(color = Color.White)
                        .exposedDropdownSize(),
                    expanded = viewModel.state.isEthnicityExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(OutletDetailsEvent.OnEthnicityDropDownClick)
                    }) {
                    Text(
                        text = stringResource(CommonR.string.select_ethnicity),
                        modifier = Modifier.padding(start = 20.r()),
                        style = subHeading1TextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().padding(top = 10.r()).background(color = Color.Black).height(1.r()))
                    ETHNICITIES.forEach { label ->
                        DropdownMenuItem(text = {
                            Text(
                                text = label,
                                color = Color.Black
                            )
                        }, onClick = {
                            viewModel.onEvent(OutletDetailsEvent.OnEthnicitySelection(label))
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    }
                }
            }

            Text(
                text = stringResource(id = CommonR.string.route_name),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )


            ExposedDropdownMenuBox(
                expanded = viewModel.state.isRouteNameExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {


                TextField(
                    value = viewModel.state.routeName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (viewModel.state.isRouteNameExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(OutletDetailsEvent.OnRouteNameDropDownClick)
                            }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Color.LightGray
                        ),
                    placeholder = {
                        Text(
                            text = ROUTE_NAMES[0],
                            style = TextStyle(
                                color = ColorTextFieldPlaceholder,
                            )
                        )
                    }
                )

                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(color = Color.White)
                        .exposedDropdownSize(),
                    expanded = viewModel.state.isRouteNameExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(OutletDetailsEvent.OnRouteNameDropDownClick)
                    }) {
                    Text(
                        text = stringResource(CommonR.string.select_route),
                        modifier = Modifier.padding(start = 20.r()),
                        style = subHeading1TextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().padding(top = 10.r()).background(color = Color.Black).height(1.r()))
                    ROUTE_NAMES.forEach { label ->
                        DropdownMenuItem(text = {
                            Text(
                                text = label,
                                color = Color.Black
                            )
                        }, onClick = {
                            viewModel.onEvent(OutletDetailsEvent.OnRouteNameSelection(label))
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    }
                }
            }

            Text(
                text = stringResource(id = CommonR.string.date_of_birth),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.birthdate,
                readOnly = true,
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
                    //viewModel.onEvent(OutletDetailsEvent.OnBirthDateEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isBirthDateError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_date_of_birth),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Sharp.DateRange,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.r())
                            .clickable {
                                openDatePickerDialog.value = true
                            },
                        tint = APP_DEFAULT_COLOR
                    )
                })


            Text(
                text = stringResource(id = CommonR.string.mobile_no_1),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.phone1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()

                    defaultKeyboardAction(ImeAction.Done)
                }),
                onValueChange = {
                    viewModel.onEvent(OutletDetailsEvent.OnMobileNo1Enter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isPhone1Error) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_mobile_no_1),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
            )



            Text(
                text = stringResource(id = CommonR.string.mobile_no_2),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.phone2,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()

                    defaultKeyboardAction(ImeAction.Done)
                }),
                onValueChange = {
                    viewModel.onEvent(OutletDetailsEvent.OnMobileNo2Enter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isPhone2Error) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_mobile_no_2),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
            )


            Text(
                text = stringResource(id = CommonR.string.trade_license),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.tradeLicense,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()

                    defaultKeyboardAction(ImeAction.Done)
                }),
                onValueChange = {
                    viewModel.onEvent(OutletDetailsEvent.OnTradeLicenseEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isTradeLicenseError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_trade_license),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
            )


            Text(
                text = stringResource(id = CommonR.string.expiry_date),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.tlcExpiryDate,
                readOnly = true,
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

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isExpiryDateError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_expity_date),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Sharp.DateRange,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.r())
                            .clickable {
                                openExpiryDatePickerDialog.value = true
                            },
                        tint = APP_DEFAULT_COLOR
                    )
                })


            Text(
                text = stringResource(id = CommonR.string.vat_trn),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.vatTRN,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()

                    defaultKeyboardAction(ImeAction.Done)
                }),
                onValueChange = {
                    viewModel.onEvent(OutletDetailsEvent.OnVatTRNEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (viewModel.state.isVatTrnError) Color.Red else Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_vat_trn),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
            )

            Text(
                text = stringResource(id = CommonR.string.payment_options),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )


            ExposedDropdownMenuBox(
                expanded = viewModel.state.isPaymentOptionsExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {


                TextField(
                    value = viewModel.state.paymentOption,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (viewModel.state.isPaymentOptionsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(OutletDetailsEvent.OnPaymentDropDownClick)
                            }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Color.LightGray
                        ),
                    placeholder = {
                        Text(
                            text = PAYMENT_OPTIONS[0],
                            style = TextStyle(
                                color = ColorTextFieldPlaceholder,
                            )
                        )
                    }
                )

                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(color = Color.White),
                    expanded = viewModel.state.isPaymentOptionsExpanded,
                    onDismissRequest = {
                        viewModel.onEvent(OutletDetailsEvent.OnPaymentDropDownClick)
                    }) {
                    Text(
                        text = stringResource(CommonR.string.select_payment_option),
                        modifier = Modifier.padding(start = 20.r()),
                        style = subHeading1TextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().padding(top = 10.r()).background(color = Color.Black).height(1.r()))
                    PAYMENT_OPTIONS.forEach { label ->
                        DropdownMenuItem(text = {
                            Text(
                                text = label,
                                color = Color.Black
                            )
                        }, onClick = {
                            viewModel.onEvent(OutletDetailsEvent.OnPaymentOptionSelection(label))
                        })
                    }
                }
            }


            Row {
                Text(
                    text = stringResource(id = CommonR.string.address),
                    style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(top = 10.r(), bottom = 5.r())
                    .clickable {
                        viewModel.onEvent(OutletDetailsEvent.OnGettingCurrentLocation)
                    }) {
                    Icon(
                        painter = painterResource(id = DesignSystemR.drawable.ic_my_location),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(id = CommonR.string.get_current_location),
                        style = smallBodyTextStyle.copy(fontWeight = FontWeight.W500)
                    )
                }

            }

            TextField(
                value = viewModel.state.address,
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
                    viewModel.onEvent(OutletDetailsEvent.OnAddressEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    //.height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),

                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_address),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.r()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = CommonR.string.location),
                    style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${viewModel.state.latitude}, ${viewModel.state.longitude}",
                    style = smallBodyTextStyle,
                    modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.r())
                    .border(
                        width = 1.r(),
                        color = if (viewModel.state.isImageError) Color.Red else Color.LightGray,
                        shape = RoundedCornerShape(10.r())
                    )
                    .clickable {
                        openImagePickerDialog.value = true
                    }
            ) {

                if (viewModel.state.image.isEmpty()) {
                    Image(
                        painter = painterResource(id = DesignSystemR.drawable.ic_camera),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                } else {

                    Image(
                        bitmap = base64ToImage(viewModel.state.image).asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                    )

                }


            }

            AppActionButtonCompose(
                stringId = CommonR.string.save_changes,
                modifier = Modifier.padding(top = 10.r())
            ) {
                viewModel.onEvent(OutletDetailsEvent.OnSubmitButtonClick(outletDetails!!.id.toString()))
            }
        }
    }

    if (openDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                viewModel.onEvent(OutletDetailsEvent.OnDatePick(it))
            }, openDialog = openDatePickerDialog
        )
    }

    if (openExpiryDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                viewModel.onEvent(OutletDetailsEvent.OnExpiryDateEnter(it))
            }, openDialog = openExpiryDatePickerDialog
        )
    }

    if (openImagePickerDialog.value) {
        ImagePickerDialog(openDialog = openImagePickerDialog, onDoneClick = { image ->
            viewModel.onEvent(
                OutletDetailsEvent.OnImageSelection(
                    image,
                    context.contentResolver
                )
            )
        })
    }

    if (viewModel.state.isLoading) {
        LoadingDialog {

        }
    }
}

@Composable
@Preview
fun PreviewOutletDetailsScreen() {
    val snackBarHostState = remember { SnackbarHostState() }
    OutletDetailsScreen(
        onBack = {},
        snackbarHostState = snackBarHostState,
        outletDetails = null
    )
}