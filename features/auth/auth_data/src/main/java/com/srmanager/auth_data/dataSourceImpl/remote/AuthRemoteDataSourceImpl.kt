package com.srmanager.auth_data.dataSourceImpl.remote

import com.srmanager.auth_data.dataSource.remote.AuthRemoteDataSource
import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.model.*
import kotlinx.serialization.InternalSerializationApi


class AuthRemoteDataSourceImpl(
    private val publicApiService: PublicApiService,
    private val privateApiService: PrivateApiService,
) : AuthRemoteDataSource {

    @OptIn(InternalSerializationApi::class)
    override suspend fun getLoginResponse(loginRequest: LoginRequest): LoginDto {
        return publicApiService.login(loginRequest)
    }
}