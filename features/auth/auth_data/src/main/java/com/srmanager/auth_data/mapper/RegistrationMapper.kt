package com.srmanager.auth_data.mapper

import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.auth.auth_domain.model.RegistrationResponse
import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.dto.RegistrationDto

fun LoginDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        userId = userId,
        access_token = access_token,
        refresh_token = refresh_token,
        userProfile = this.userProfile
    )
}

