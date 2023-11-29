package com.srmanager.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val confirmPassword: String,
    val deviceId: String?=null,
    val email: String,
    val language: String,
    val password: String,
    val rememberMe: Boolean=true
)