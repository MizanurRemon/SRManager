package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.auth.auth_domain.repository.AuthRepository

class UpdateEmailUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<LoginResponse> {
        return authRepository.updateEmail(email)
    }
}