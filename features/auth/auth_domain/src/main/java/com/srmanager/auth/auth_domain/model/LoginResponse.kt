package com.srmanager.auth.auth_domain.model

import com.srmanager.core.network.dto.UserProfile

data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val userId: Int,
    val userProfile: UserProfile
)