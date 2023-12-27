package com.srmanager.auth_presentation.login

sealed class LoginEvent {
    data class OnUserNameEnter(val userName: String): LoginEvent()
    data class OnPasswordEnter(val password: String): LoginEvent()
    object OnSubmitClick: LoginEvent()
    object OnSignUpClick: LoginEvent()
}
