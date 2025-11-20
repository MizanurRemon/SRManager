package com.srmanager.auth_data.mapper

import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.core.network.dto.LoginDto
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun LoginDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        companyId = data.companyId,
        token = data.token,
        httpStatus = httpStatus,
        message = message
    )
}

