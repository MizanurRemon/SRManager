package com.srmanager.auth_data.dataSource.remote

import com.srmanager.core.network.dto.AuthenticationDraftDto
import com.srmanager.core.network.dto.RegistrationDto
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.dto.UserWaitingDto
import com.srmanager.core.network.model.*


interface AuthRemoteDataSource {

    suspend fun getLoginResponse(loginRequest: LoginRequest): LoginDto
}