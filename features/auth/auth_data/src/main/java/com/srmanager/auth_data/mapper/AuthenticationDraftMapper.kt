package com.srmanager.auth_data.mapper

import com.srmanager.auth.auth_domain.model.AuthenticationDraftResponse
import com.srmanager.core.network.dto.AuthenticationDraftDto

fun AuthenticationDraftDto.toResponse(): AuthenticationDraftResponse {
    return AuthenticationDraftResponse(
        userId = userId
    )
}