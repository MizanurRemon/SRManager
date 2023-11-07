package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.NewPasswordRequest

class ResetPasswordUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        password: String,
        confirmPassword: String,
    ): Result<CommonResponseDto> {
        return authRepository.passwordReset(NewPasswordRequest(password, confirmPassword))
    }
}