package com.srmanager.auth_presentation.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.designsystem.components.AppActionButtonCompose
import com.srmanager.core.designsystem.components.LoadingDialog
import com.srmanager.core.designsystem.h
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.theme.*
import com.srmanager.core.designsystem.w
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onBack: () -> Unit,
    toHome: () -> Unit,
    toSignUp: () -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val noteText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.W300)) {
            append(stringResource(id = CommonR.string.donot_have_account) + " ")
        }

        pushStringAnnotation(
            tag = CommonR.string.sign_up_for_free.toString(),
            annotation = CommonR.string.sign_up_for_free.toString()
        )

        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.W700,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(stringResource(id = CommonR.string.sign_up_for_free).replace(".", ""))
        }

        append(".")
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {

                    toHome()

                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short,
                    )
                }

                is UiEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }

        }
    }
    return Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = AppBrush)
            .padding(top = 80.h(), start = 35.w(), end = 35.w()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = DesignSystemR.drawable.app_icon
            ),
            contentDescription = "",
            modifier = Modifier
                .padding(vertical = 20.h())
                .size(80.w())
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp))
        )

        Text(
            text = stringResource(id = CommonR.string.sign_in),
            style = heading2TextStyle,
            modifier = Modifier.padding(vertical = 20.h())
        )

        Text(
            text = stringResource(id = CommonR.string.email),
            style = TextStyle(
                fontFamily = fontRoboto,
                fontSize = 16.ssp(),
                color = Color(0xff151B33),
                fontWeight = FontWeight.W500,
                lineHeight = 21.ssp(),
                textAlign = TextAlign.Left
            ),
            modifier = Modifier
                .padding(bottom = 10.h())
                .align(Alignment.Start),
        )
        TextField(
            value = viewModel.state.email,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                viewModel.onEvent(LoginEvent.OnEmailEnter(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(15.dp))
                .onFocusChanged {
                    viewModel.isEmailValid()
                }
                .conditional(viewModel.state.isEmailValid) {
                    return@conditional border(
                        width = 1.dp,
                        color = Color(0xFFE2E4EA),
                        shape = RoundedCornerShape(15.dp)
                    )
                }
                .conditional(!viewModel.state.isEmailValid) {
                    return@conditional border(
                        width = 1.dp,
                        color = ColorError,
                        shape = RoundedCornerShape(15.dp)
                    )
                },
            leadingIcon = {
                Image(
                    painter = painterResource(id = DesignSystemR.drawable.ic_email),
                    contentDescription = "",
                    modifier = Modifier.size(24.r())
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = CommonR.string.enter_your_email), style = TextStyle(
                        color = ColorTextFieldPlaceholder,
                    )
                )
            },
        )
        if (!viewModel.state.isEmailValid) {
            Text(
                text = context.getString(CommonR.string.invalid_email),
                style = TextStyle(
                    fontWeight = FontWeight.W300,
                    fontFamily = fontRoboto,
                    lineHeight = 24.ssp(),
                    fontSize = 15.ssp(),
                    textAlign = TextAlign.Left,
                    color = ColorError
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.h())
                    .align(Alignment.Start),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = CommonR.string.password),
                style = TextStyle(
                    fontFamily = fontRoboto,
                    fontSize = 16.sp,
                    color = Color(0xff151B33),
                    fontWeight = FontWeight.W500,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Left
                ),
            )
            Text(
                text = stringResource(id = CommonR.string.forget_password_exla),
                style = TextStyle(
                    fontFamily = fontRoboto,
                    fontSize = 16.sp,
                    color = ColorTextPrimary,
                    fontWeight = FontWeight.W300,
                    lineHeight = 19.sp,
                    textAlign = TextAlign.Right
                ),
                modifier = Modifier.clickable {
                    navController.navigate(Route.FORGET_PASS_EMAIL_INPUT)
                }
            )
        }
        TextField(
            value = viewModel.state.password,
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
                viewModel.onEvent(LoginEvent.OnSubmitClick)
                defaultKeyboardAction(ImeAction.Done)
            }),
            onValueChange = {
                viewModel.onEvent(LoginEvent.OnPasswordEnter(it))
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(15.dp))
                .conditional(viewModel.state.isPasswordValid) {
                    return@conditional border(
                        width = 1.dp,
                        color = Color(0xFFE2E4EA),
                        shape = RoundedCornerShape(15.dp)
                    )
                }
                .conditional(!viewModel.state.isPasswordValid) {
                    return@conditional border(
                        width = 1.dp,
                        color = ColorError,
                        shape = RoundedCornerShape(15.dp)
                    )
                },
            leadingIcon = {
                Image(
                    painter = painterResource(id = DesignSystemR.drawable.ic_lock),
                    contentDescription = "",
                    modifier = Modifier.size(24.r())
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = CommonR.string.enter_your_password),
                    style = TextStyle(
                        color = ColorTextFieldPlaceholder,
                    )
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (showPassword.value) DesignSystemR.drawable.ic_eye_slash else DesignSystemR.drawable.ic_eye_unslash),
                    contentDescription = "", tint = ColorTextFieldPlaceholder,
                    modifier = Modifier.clickable {
                        showPassword.value = !showPassword.value
                    }
                )
            },
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        )

        if (!viewModel.state.isPasswordValid) {
            Text(
                text = context.getString(CommonR.string.invalid_password),
                style = TextStyle(
                    fontWeight = FontWeight.W300,
                    fontFamily = fontRoboto,
                    lineHeight = 24.sp,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Left,
                    color = ColorError
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.h())
                    .align(Alignment.Start),
            )
        }

        Spacer(modifier = Modifier.height(20.r()))


        AppActionButtonCompose(stringId = CommonR.string.sign_in) {
            //viewModel.onEvent(LoginEvent.OnSubmitClick)

            toHome()
        }

        //Spacer(modifier = Modifier.weight(1f))
        /*ClickableText(
            modifier = Modifier.padding(top = 40.h(), bottom = 55.h()),
            text = noteText,
            style = bodyRegularTextStyle.copy(
                color = ColorTextPrimary,
                // letterSpacing = .5.sp,
                textAlign = TextAlign.Center
            ), onClick = { offset ->
                noteText.getStringAnnotations(
                    tag = CommonR.string.sign_up_for_free.toString(),
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    toSignUp()
                }
            }
        )*/

        BackHandler {
            onBack()
        }

        if (viewModel.state.isShowDialog)
            LoadingDialog {}
    }
}