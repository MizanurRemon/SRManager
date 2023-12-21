package com.srmanager.auth.auth_domain.repository

import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.core.network.model.LoginRequest

interface AuthRepository {

    suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginResponse>

}