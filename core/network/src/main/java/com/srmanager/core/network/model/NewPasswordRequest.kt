package com.srmanager.core.network.model

data class NewPasswordRequest(
    val confirmPassword: String,
    val password: String
)