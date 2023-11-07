package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.model.AuthenticationDraftResponse
import com.srmanager.auth.auth_domain.model.AuthenticationModel
import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.model.AuthenticationDraftRequest

class AuthenticationDraftUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(authenticationModel: AuthenticationModel): Result<AuthenticationDraftResponse> {
        return authRepository.authenticationDraftCheck(
            AuthenticationDraftRequest(
                username = authenticationModel.username,
                password = authenticationModel.password,
                deviceId = authenticationModel.deviceId,
                rememberMe = authenticationModel.rememberMe
            )
        )
    }
}