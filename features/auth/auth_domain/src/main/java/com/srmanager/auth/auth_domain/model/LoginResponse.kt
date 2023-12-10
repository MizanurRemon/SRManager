package com.srmanager.auth.auth_domain.model

import com.srmanager.core.network.dto.UserProfile

data class LoginResponse(
    val data: String,
    val httpStatus: Int,
    val message: String
)