package com.srmanager.auth_data.mapper

import com.srmanager.core.network.dto.UserWaitingDto
import com.srmanager.auth.auth_domain.model.UserWaitingResponse

fun UserWaitingDto.toResponse(): UserWaitingResponse {
    return UserWaitingResponse(
        id = id ?: 0
    )
}