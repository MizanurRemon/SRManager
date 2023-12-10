package com.srmanager.auth.auth_domain.repository

import com.srmanager.auth.auth_domain.model.AuthenticationDraftResponse
import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.auth.auth_domain.model.RegistrationResponse
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.AuthenticationDraftRequest
import com.srmanager.core.network.model.LoginRequest
import com.srmanager.core.network.model.NewPasswordRequest
import com.srmanager.core.network.model.RegistrationRequest
import com.srmanager.core.network.model.ResetPasswordRequest

interface AuthRepository {

    suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginResponse>

}