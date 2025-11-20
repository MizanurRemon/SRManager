package com.srmanager.auth_data.dataSource.remote

import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.model.*
import kotlinx.serialization.InternalSerializationApi


interface AuthRemoteDataSource {

    @OptIn(InternalSerializationApi::class)
    suspend fun getLoginResponse(loginRequest: LoginRequest): LoginDto
}