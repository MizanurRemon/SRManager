package com.srmanager.outlet_presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.MyDatePickerDialog
import com.srmanager.core.designsystem.theme.bodyBoldTextStyle
import com.srmanager.core.designsystem.w
import com.srmanager.outlet_presentation.viewmodel.OutletEvent
import com.srmanager.outlet_presentation.viewmodel.OutletViewModel
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OutletScreen(onBack: () -> Unit, viewModel: OutletViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var openDatePickerDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                viewModel.onEvent(OutletEvent.OnAddButtonClick)
            },
            modifier = Modifier.padding(end = 20.r(), bottom = 40.r()),
            containerColor = APP_DEFAULT_COLOR
        ) {
            Icon(
                painterResource(id = DesignSystemR.drawable.ic_customer_add),
                contentDescription = null,
                modifier = Modifier.size(30.r()),
                tint = Color.White
            )
        }
    }, floatingActionButtonPosition = FabPosition.End) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppToolbarCompose(
                onClick = { onBack() },
                icon = DesignSystemR.drawable.ic_back,
                title = CommonR.string.back
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = CommonR.string.outlet),
                    style = bodyBoldTextStyle.copy(color = Color.Black)
                )
            }
        }
    }

    if (viewModel.state.isShowEntryDialog.value) {
        Dialog(properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
            onDismissRequest = {
                viewModel.state.isShowEntryDialog.value = false
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.w())
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.r())
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = APP_DEFAULT_COLOR,
                            shape = RoundedCornerShape(topStart = 20.r(), topEnd = 20.r())
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.r())

                    ) {

                        Text(
                            text = "Shop Entry Form",
                            style = bodyBoldTextStyle.copy(
                                color = Color.White,
                                letterSpacing = .2.sp
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                viewModel.state.isShowEntryDialog.value = false
                            },
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.r())
                ) {
                    TextField(
                        value = viewModel.state.outletName,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()

                            defaultKeyboardAction(ImeAction.Done)
                        }),
                        onValueChange = {
                            viewModel.onEvent(OutletEvent.OnOutletNameEnter(it))
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
                        }
                    )

                    TextField(
                        value = viewModel.state.ownerName,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()

                            defaultKeyboardAction(ImeAction.Done)
                        }),
                        onValueChange = {
                            viewModel.onEvent(OutletEvent.OnOwnerNameEnter(it))
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
                        }
                    )

                    TextField(
                        value = viewModel.state.birthdate,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()

                            defaultKeyboardAction(ImeAction.Done)
                        }),
                        onValueChange = {
                            viewModel.onEvent(OutletEvent.OnBirthDateEnter(it))
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
                        }
                    )

                    Spacer(modifier = Modifier.height(10.r()))

                    AppActionButtonCompose(stringId = CommonR.string.done) {

                    }
                }
            }

        }

    }

    if (openDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                viewModel.onEvent(OutletEvent.OnDatePick(it))
            }, openDialog = openDatePickerDialog
        )
    }
}

@Composable
@Preview
fun PreviewCustomerAddScreen() {
    OutletScreen(onBack = {})
}