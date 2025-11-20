package com.srmanager.core.network

import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.dto.TokenDto
import com.srmanager.core.network.model.*
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.*

interface PublicApiService {

    @OptIn(InternalSerializationApi::class)
    @POST("bsol/api/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginDto


    @POST("token/refresh")
    suspend fun refreshToken(
        @Body tokenRequest: TokenRequest,
    ): TokenDto

}