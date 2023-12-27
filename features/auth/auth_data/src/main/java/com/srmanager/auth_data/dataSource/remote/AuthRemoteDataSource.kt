package com.srmanager.auth_data.dataSource.remote

import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.model.*


interface AuthRemoteDataSource {

    suspend fun getLoginResponse(loginRequest: LoginRequest): LoginDto
}