package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.auth.auth_domain.model.LoginModel
import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.core.network.model.LoginRequest

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(loginModel: LoginModel): Result<LoginResponse> {
        return authRepository.getLoginResponse(
            LoginRequest(
                userName = loginModel.email,
                password = loginModel.password
            )
        )
    }
}