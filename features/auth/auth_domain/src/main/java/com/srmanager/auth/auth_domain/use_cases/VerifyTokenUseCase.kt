package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.dto.CommonResponseDto

class VerifyTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(token: String): Result<CommonResponseDto> {
        return authRepository.getVerifyTokenResponse(token)

    }
}