package com.srmanager.auth_data.mapper

import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.core.network.dto.LoginDto

fun LoginDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        data = data.toString(),
        httpStatus = httpStatus,
        message = message
    )
}

