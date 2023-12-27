package com.srmanager.auth.auth_domain.model

data class RegistrationModel(
    val confirmPassword: String,
    val email: String,
    val language: String,
    val password: String,
)