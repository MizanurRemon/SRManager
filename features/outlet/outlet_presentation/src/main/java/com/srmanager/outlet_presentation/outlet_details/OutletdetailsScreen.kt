package com.srmanager.outlet_presentation.outlet_details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.srmanager.core.common.util.ETHNICITIES
import com.srmanager.core.common.util.MARKET_NAMES
import com.srmanager.core.common.util.PAYMENT_OPTIONS
import com.srmanager.core.common.util.ROUTE_NAMES
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.base64ToImage
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.ImagePickerDialog
import com.srmanager.core.designsystem.theme.MyDatePickerDialog
import com.srmanager.core.designsystem.theme.smallBodyTextStyle
import com.srmanager.core.designsystem.theme.subHeading1TextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OutletDetailsScreen(
    onBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
    state: OutletDetailsState,
    uiEvent: Flow<UiEvent>,
    onEvent: (OutletDetailsEvent)-> Unit,
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
        uiEvent.collect { event ->
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



    /*LaunchedEffect(key1 = true) {
        getOutletDetails(outletID = outletDetails!!.id.toString())
    }*/

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
            TextField(value = state.outletName,
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
                    onEvent(OutletDetailsEvent.OnOutletNameEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isOutletNameError) Color.Red else Color.LightGray
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

            TextField(value = state.ownerName,
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
                    onEvent(OutletDetailsEvent.OnOwnerNameEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isOwnerNameError) Color.Red else Color.LightGray
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

            TextField(value = state.email,
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
                    onEvent(OutletDetailsEvent.OnEmailEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isEmailError) Color.Red else Color.LightGray
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
                expanded = state.isMarketNameExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {


                TextField(
                    value = state.marketName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (state.isMarketNameExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                onEvent(OutletDetailsEvent.OnMarketNameDropDownClick)
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
                    expanded = state.isMarketNameExpanded,
                    onDismissRequest = {
                        onEvent(OutletDetailsEvent.OnMarketNameDropDownClick)
                    }) {
                    Text(
                        text = stringResource(CommonR.string.select_market),
                        modifier = Modifier.padding(start = 20.r()),
                        style = subHeading1TextStyle.copy(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().padding(top = 10.r()).background(color = Color.Black).height(1.r()))
                    state.marketNameList.forEach { response ->
                        DropdownMenuItem(text = {
                            Text(
                                text = response.text.toString(),
                                color = Color.Black
                            )
                        }, onClick = {
                            onEvent(OutletDetailsEvent.OnMarketNameSelection(response.text.toString()))
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
                expanded = state.isEthnicityExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {

                TextField(
                    value = state.ethnicity,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (state.isEthnicityExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                onEvent(OutletDetailsEvent.OnEthnicityDropDownClick)
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
                    expanded = state.isEthnicityExpanded,
                    onDismissRequest = {
                        onEvent(OutletDetailsEvent.OnEthnicityDropDownClick)
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
                            onEvent(OutletDetailsEvent.OnEthnicitySelection(label))
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
                expanded = state.isRouteNameExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {


                TextField(
                    value = state.routeName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (state.isRouteNameExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                onEvent(OutletDetailsEvent.OnRouteNameDropDownClick)
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
                    expanded = state.isRouteNameExpanded,
                    onDismissRequest = {
                        onEvent(OutletDetailsEvent.OnRouteNameDropDownClick)
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
                            onEvent(OutletDetailsEvent.OnRouteNameSelection(label))
                        }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    }
                }
            }

            Text(
                text = stringResource(id = CommonR.string.date_of_birth),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = state.birthdate,
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
                    //onEvent(OutletDetailsEvent.OnBirthDateEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isBirthDateError) Color.Red else Color.LightGray
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
                value = state.phone1,
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
                    onEvent(OutletDetailsEvent.OnMobileNo1Enter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isPhone1Error) Color.Red else Color.LightGray
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
                value = state.phone2,
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
                    onEvent(OutletDetailsEvent.OnMobileNo2Enter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isPhone2Error) Color.Red else Color.LightGray
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
                value = state.tradeLicense,
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
                    onEvent(OutletDetailsEvent.OnTradeLicenseEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isTradeLicenseError) Color.Red else Color.LightGray
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

            TextField(value = state.tlcExpiryDate,
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
                        color = if (state.isExpiryDateError) Color.Red else Color.LightGray
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
                value = state.vatTRN,
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
                    onEvent(OutletDetailsEvent.OnVatTRNEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = if (state.isVatTrnError) Color.Red else Color.LightGray
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
                expanded = state.isPaymentOptionsExpanded,
                onExpandedChange = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {


                TextField(
                    value = state.paymentOption,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            if (state.isPaymentOptionsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                            tint = APP_DEFAULT_COLOR,
                            modifier = Modifier.clickable {
                                onEvent(OutletDetailsEvent.OnPaymentDropDownClick)
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
                    expanded = state.isPaymentOptionsExpanded,
                    onDismissRequest = {
                        onEvent(OutletDetailsEvent.OnPaymentDropDownClick)
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
                            onEvent(OutletDetailsEvent.OnPaymentOptionSelection(label))
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
                        onEvent(OutletDetailsEvent.OnGettingCurrentLocation)
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
                value = state.address,
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
                    onEvent(OutletDetailsEvent.OnAddressEnter(it))
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

            Text(
                text = stringResource(id = CommonR.string.billing_address),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = state.billingAddress,
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
                    onEvent(OutletDetailsEvent.OnBillingAddressEnter(it))
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
                        text = stringResource(id = CommonR.string.enter_billing_address),
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
                    text = "${state.latitude}, ${state.longitude}",
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
                        color = if (state.isImageError) Color.Red else Color.LightGray,
                        shape = RoundedCornerShape(10.r())
                    )
                    .clickable {
                        openImagePickerDialog.value = true
                    }
            ) {

                if (state.image.isEmpty()) {
                    Image(
                        painter = painterResource(id = DesignSystemR.drawable.ic_camera),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                } else {

                    Image(
                        bitmap = base64ToImage(state.image).asImageBitmap(),
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
                onEvent(OutletDetailsEvent.OnSubmitButtonClick(state.id.toString()))
            }
        }
    }

    if (openDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                onEvent(OutletDetailsEvent.OnDatePick(it))
            }, openDialog = openDatePickerDialog
        )
    }

    if (openExpiryDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                onEvent(OutletDetailsEvent.OnExpiryDateEnter(it))
            }, openDialog = openExpiryDatePickerDialog
        )
    }

    if (openImagePickerDialog.value) {
        ImagePickerDialog(openDialog = openImagePickerDialog, onDoneClick = { image ->
            onEvent(
                OutletDetailsEvent.OnImageSelection(
                    image,
                    context.contentResolver
                )
            )
        })
    }

    if (state.isLoading) {
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
        state = OutletDetailsState(),
        uiEvent = flow {  },
        onEvent = {},
    )
}