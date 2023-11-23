package com.srmanager.outlet_presentation.outlet_add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.MyDatePickerDialog
import com.srmanager.core.designsystem.theme.smallBodyTextStyle
import com.srmanager.core.designsystem.w
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OutletAddScreen(onBack: () -> Unit, viewModel: OutletAddViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val openDatePickerDialog = remember {
        mutableStateOf(false)
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color.Black)
            )
            Text(
                text = stringResource(id = CommonR.string.outlet_name),
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )
            TextField(value = viewModel.state.outletName,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnOutletNameEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = DesignSystemR.drawable.ic_shop),
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.ownerName,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnOwnerNameEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = DesignSystemR.drawable.ic_shop),
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_owner_name),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                })



            Text(
                text = stringResource(id = CommonR.string.date_of_birth),
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.birthdate,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnBirthDateEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Sharp.DateRange,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.phone1,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnBirthDateEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.phone2,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnMobileNo2Enter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.tradeLicense,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnTradeLicenseEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(value = viewModel.state.tlcExpiryDate,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnExpiryDateEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Sharp.DateRange,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                text = stringResource(id = CommonR.string.vat_trn),
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.vatTRN,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnTradeLicenseEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
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
                text = stringResource(id = CommonR.string.address),
                style = smallBodyTextStyle,
                modifier = Modifier.padding(top = 10.r(), bottom = 5.r())
            )

            TextField(
                value = viewModel.state.address,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
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
                    viewModel.onEvent(OutletAddEvent.OnAddressEnter(it))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.LightGray
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "",
                        modifier = Modifier.size(24.r()),
                        tint = Color.LightGray
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = CommonR.string.enter_address),
                        style = TextStyle(
                            color = ColorTextFieldPlaceholder,
                        )
                    )
                },
            )

            AppActionButtonCompose(
                stringId = CommonR.string.done,
                modifier = Modifier.padding(top = 10.r())
            ) {

            }
        }
    }

    if (openDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                viewModel.onEvent(OutletAddEvent.OnDatePick(it))
            }, openDialog = openDatePickerDialog
        )
    }
}