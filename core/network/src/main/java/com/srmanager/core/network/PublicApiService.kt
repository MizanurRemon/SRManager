package com.srmanager.core.network

import com.srmanager.core.network.dto.DomainDto
import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.dto.TokenDto
import com.srmanager.core.network.model.*
import retrofit2.http.*

interface PublicApiService {

    @POST("bsol/api/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginDto


    @POST("token/refresh")
    suspend fun refreshToken(
        @Body tokenRequest: TokenRequest,
    ): TokenDto
    

    @GET("public/api/votes/domain-names/{partialText}")
    suspend fun getDomainList(
        @Path("partialText", encoded = true) partialText: String,
    ): List<DomainDto>


}