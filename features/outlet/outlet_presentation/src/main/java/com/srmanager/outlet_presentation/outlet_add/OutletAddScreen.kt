package com.srmanager.outlet_presentation.outlet_add

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.srmanager.core.common.util.CoilImageGetter
import com.srmanager.core.common.util.getBitmapFromImage
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.AppToolbarCompose
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.theme.APP_DEFAULT_COLOR
import com.srmanager.core.designsystem.theme.ColorTextFieldPlaceholder
import com.srmanager.core.designsystem.theme.ImagePickerDialog
import com.srmanager.core.designsystem.theme.MyDatePickerDialog
import com.srmanager.core.designsystem.theme.smallBodyTextStyle
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

    val openExpiryDatePickerDialog = remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val openImagePickerDialog = remember {
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
                    .height(250.r())
                    .border(
                        width = 1.r(),
                        color = if (viewModel.state.isImageError) Color.Red else Color.LightGray,
                        shape = RoundedCornerShape(20.r())
                    )
                    .clickable {
                        openImagePickerDialog.value = true
                    }
            ) {

                if (imageUri == null) {
                    Image(
                        painter = painterResource(id = DesignSystemR.drawable.ic_camera),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)

                    )
                } else {
                    viewModel.onEvent(
                        OutletAddEvent.OnImageSelection(
                            imageUri!!,
                            context.contentResolver
                        )
                    )

                    Image(
                        painter = rememberImagePainter(data = imageUri),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight()
                    )

                }


            }
            Text(
                text = stringResource(id = CommonR.string.outlet_name),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
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
                text = stringResource(id = CommonR.string.date_of_birth),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
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
                    //viewModel.onEvent(OutletAddEvent.OnBirthDateEnter(it))
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
                    viewModel.onEvent(OutletAddEvent.OnMobileNo1Enter(it))
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
                    viewModel.onEvent(OutletAddEvent.OnVatTRNEnter(it))
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
                text = stringResource(id = CommonR.string.address),
                style = smallBodyTextStyle.copy(fontWeight = FontWeight.Light),
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

            AppActionButtonCompose(
                stringId = CommonR.string.done,
                modifier = Modifier.padding(top = 10.r())
            ) {
                viewModel.onEvent(OutletAddEvent.OnSubmitButtonClick)
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

    if (openExpiryDatePickerDialog.value) {
        MyDatePickerDialog(
            onDateSelected = {
                viewModel.onEvent(OutletAddEvent.OnExpiryDateEnter(it))
            }, openDialog = openExpiryDatePickerDialog
        )
    }

    if (openImagePickerDialog.value) {
        ImagePickerDialog(openDialog = openImagePickerDialog, onDoneClick = { image ->
            imageUri = image
        })
    }
}