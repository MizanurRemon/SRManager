package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.model.RegistrationModel
import com.srmanager.auth.auth_domain.model.RegistrationResponse
import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.model.RegistrationRequest

class RegistrationUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(registrationModel: RegistrationModel): Result<RegistrationResponse> {
        return authRepository.getRegistrationResponse(
            RegistrationRequest(
                email = registrationModel.email,
                password = registrationModel.password,
                confirmPassword = registrationModel.confirmPassword,
                language = registrationModel.language,
            )
        )
    }
}