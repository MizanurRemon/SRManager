package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.dto.CommonResponseDto

class ResendVerificationEmilUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email : String, forProfileUpdate: Boolean): Result<CommonResponseDto> {
        return authRepository.resendVerificationEmail(email, forProfileUpdate)

    }
}