package com.srmanager.core.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val ColorPrimaryDark = Color(0xFF005DFF)
val ColorError = Color(0xFFFF0000)
val ColorTextPrimary = Color(0xff021561)
val ColorTextSecondary = Color(0xff606888)
val ColorTextFieldPlaceholder = Color(0xffA7AEC1)
val DISABLED_COLOR = Color(0xFFCECECE)
val APP_DEFAULT_COLOR = Color(0xFF00C344)
val APP_DEFAULT_BUTTON_COLOR = Color(0xFF00C344)
val DRAWER_SELECTED =  Color(0x16512E91)
//00c344

val AppBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFFFFFF),
        Color(0xFFFFFFFF),
    ),
)
val AppBrushReversed = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFF0F0F0),
        Color(0xFFF3F6FF),
        Color(0xFFDBE3FF),
    ),
)
val MaliciousBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xffFFD4C6),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),

        ),
)
val SuspiciousBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xffFFEBC0),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),
        Color(0xffFFFFFF),
    ),
)
val HighGreenBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFF87CE6D),
        Color(0xFF82DC61),
    ),
)

val MediumOrangeBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFA3D00),
        Color(0xFFFFAC00),
        Color(0xFFFAAC03),
        Color(0xFFDBE9FF),
        Color(0xFFDCE9FF),
    ),
)

val LowRedBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFA3D00),
        Color(0xFFDBE9FF),
        Color(0xFFDCE9FF),
        Color(0xFFDBE9FF),
        Color(0xFFDCE9FF),
    ),
)





