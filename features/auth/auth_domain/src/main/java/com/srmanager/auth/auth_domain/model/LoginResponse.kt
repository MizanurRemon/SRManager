package com.srmanager.auth.auth_domain.model

data class LoginResponse(
    val data: String,
    val httpStatus: Int,
    val message: String
)