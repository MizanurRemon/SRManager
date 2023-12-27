package com.srmanager.auth_presentation.login

data class LoginState(
    val userName: String = "",
    val password: String = "",
    val isUserNameValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isShowDialog: Boolean = false,
)
