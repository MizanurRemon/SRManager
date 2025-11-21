package com.srmanager.auth.auth_domain.model

data class LoginResponse(
    val token: String,
    val httpStatus: Int,
    val message: String,
    val companyId: Int
)