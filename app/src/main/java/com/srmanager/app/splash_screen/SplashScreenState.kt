package com.srmanager.app.splash_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SplashScreenState(
    val address: MutableState<String> = mutableStateOf(""),
    val isLoading: Boolean = true
)
