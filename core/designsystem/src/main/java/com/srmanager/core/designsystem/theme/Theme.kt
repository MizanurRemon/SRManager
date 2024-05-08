package com.srmanager.core.designsystem.theme

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.srmanager.core.common.util.convertMillisToDate
import com.srmanager.core.common.util.currentDate
import com.srmanager.core.designsystem.r
import com.srmanager.core.designsystem.ssp
import com.srmanager.core.designsystem.w
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDark,
    surfaceTint = ColorPrimaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimaryDark,
    surfaceTint = ColorPrimaryDark
)

@Composable
fun BaseTheme(
    darkTheme: Boolean = false,//isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.setSystemBarsColor(
        color = APP_DEFAULT_COLOR
    )
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}


@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

val darkButtonTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = Color.White,
    fontWeight = FontWeight.Bold,
    lineHeight = 23.ssp(),
    letterSpacing = .5.sp,
    textAlign = TextAlign.Left,
)

val heading1TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 28.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    textAlign = TextAlign.Center,
    lineHeight = 33.ssp(),
)

//styleName: Body light;
val bodyLightTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W300,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)

//styleName: Body light;
val bodyLMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 18.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val heading2TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 32.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 33.ssp(),
)

val preTitleTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 20.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 36.ssp(),
)
val boldBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 28.ssp(),
)


val normalBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.Normal,
    lineHeight = 28.ssp(),
)


val smallBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.Normal,
    lineHeight = 24.ssp(),
)

//styleName: Body regular;
val bodyRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W300,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Center
)
val bodyRegularSpanStyle = SpanStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W300,
)

val subHeading1TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 21.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Center
)
val subHeading1SpanStyle = SpanStyle(
    fontFamily = fontRoboto,
    fontSize = 21.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700
)

val bodyXXSRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 12.ssp(),
    color = ColorTextSecondary,
    fontWeight = FontWeight.W300,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val bodyXXSBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 12.ssp(),
    color = Color(0xff3E6FCC),
    fontWeight = FontWeight.W700,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val bodyXSBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)


val bodyXSRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    color = ColorTextSecondary,
    fontWeight = FontWeight.W300,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)


val subHeadingFormTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 19.ssp(),
    textAlign = TextAlign.Left
)


val largeBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W700,
    lineHeight = 28.ssp(),
)

val smallButtonOrLinkTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 28.ssp(),
)

val labelTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    color = ColorTextPrimary,
    fontWeight = FontWeight.W500,
    lineHeight = 28.ssp(),
    letterSpacing = 0.1.sp
)

fun String.parseBold(): AnnotatedString {
    val parts = this.split("<b>", "</b>")
    return buildAnnotatedString {
        var bold = false
        for (part in parts) {
            if (bold) {
                withStyle(
                    style = SpanStyle(
                        fontFamily = fontRoboto,
                        fontWeight = FontWeight.Bold,
                        //fontSize = 14.ssp()
                    )
                ) {
                    append(part)
                }
            } else {
                append(part)
            }
            bold = !bold
        }
    }
}

@Composable
fun AppCancelButtonCompose(
    modifier: Modifier = Modifier,
    @StringRes titleStringResId: Int,
    fontSize: TextUnit = 16.ssp(),
    lineHeight: TextUnit = 28.ssp(),
    radius: Dp = 37.dp,
    color: Color = ColorError,
    fontWeight: FontWeight = FontWeight.W700,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(color = Color.White)
            .border(
                1.dp,
                color,
                shape = RoundedCornerShape(radius),
            )
    ) {
        Text(
            text = stringResource(id = titleStringResId),
            style = TextStyle(
                fontFamily = fontRoboto,
                fontSize = fontSize,
                color = color,
                fontWeight = fontWeight,
                lineHeight = lineHeight,
                textAlign = TextAlign.Center
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    openDialog: MutableState<Boolean>,
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }
        })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: currentDate()


    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    onDateSelected(selectedDate)
                    openDialog.value = false
                }) {
                    Text(text = stringResource(id = CommonR.string.done))
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                }) {
                    Text(text = stringResource(id = CommonR.string.dissmiss))
                }
            }
        ) {
            DatePicker(
                title = {
                    Text(
                        modifier = Modifier.padding(20.r()),
                        text = stringResource(id = CommonR.string.pick_date)
                    )
                },
                state = datePickerState,
            )
        }
    }
}

@Composable
fun ImagePickerDialog(openDialog: MutableState<Boolean>, onDoneClick: (Uri) -> Unit) {

    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            imageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }

    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri!!
        }


    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.w())
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.r())
                            .padding(bottom = 10.r())
                    ) {
                        Image(
                            painter = if (imageUri == null) painterResource(id = DesignSystemR.drawable.ic_camera) else rememberImagePainter(
                                data = imageUri
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxHeight()
                                .padding(10.r())
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.r())
                            .background(color = Color.Gray)
                        //.padding(vertical = 5.r())
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.r()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Icon(
                            painter = painterResource(id = DesignSystemR.drawable.ic_check),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.r())
                                .clickable {
                                    if (imageUri == null) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Please select image",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    } else {
                                        openDialog.value = false
                                        onDoneClick(imageUri!!)
                                    }
                                },
                            tint = Color.Green
                        )

                        Icon(
                            painter = painterResource(id = DesignSystemR.drawable.ic_camera),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.r())
                                .clickable {
                                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    )

                                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                        cameraLauncher.launch(uri)
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                        )

                        Icon(
                            painter = painterResource(id = DesignSystemR.drawable.ic_gallery),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.r())
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                },
                        )

                        Icon(
                            painter = painterResource(id = DesignSystemR.drawable.ic_cross),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.r())
                                .clickable {
                                    openDialog.value = false
                                },
                            tint = Color.Red
                        )

                    }

                }
            }
        }
    }

}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyMMddHHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

@Composable
fun GpsStatusDialog(openDialog: MutableState<Boolean>, onClick: () -> Unit) {
    if (!openDialog.value) {
        Dialog(properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
            onDismissRequest = {
                openDialog.value = false
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.w())
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(32.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {

                    Text(
                        text = stringResource(id = CommonR.string.gps_disabled),
                        style = subHeading1TextStyle.copy(textAlign = TextAlign.Start),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.r()))

                    Text(
                        text = stringResource(id = CommonR.string.gps_disabled_text),
                        style = bodyRegularTextStyle.copy(textAlign = TextAlign.Start),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = { onClick() }) {
                            Text(text = stringResource(id = CommonR.string.got_it))
                        }
                    }
                }
            }
        }
    }
}