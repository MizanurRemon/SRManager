package com.srmanager.auth_presentation.registration

data class RegistrationState(
    val confirmPassword: String = "",
    val email: String = "",
    val language: String = "",
    val password: String = "",
    val token: String = "",
    val showDialog: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isConfirmPasswordValid: Boolean = false,
)
