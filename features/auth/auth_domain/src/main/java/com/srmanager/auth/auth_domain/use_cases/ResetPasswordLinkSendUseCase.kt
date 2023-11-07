package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.ResetPasswordRequest

class ResetPasswordLinkSendUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<CommonResponseDto> {
        return authRepository.resetPasswordLinkSend(ResetPasswordRequest(email))

    }
}