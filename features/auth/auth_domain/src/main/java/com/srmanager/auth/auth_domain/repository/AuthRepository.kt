package com.srmanager.auth.auth_domain.repository

import com.srmanager.auth.auth_domain.model.AuthenticationDraftResponse
import com.srmanager.auth.auth_domain.model.LoginResponse
import com.srmanager.auth.auth_domain.model.RegistrationResponse
import com.srmanager.auth.auth_domain.model.UserWaitingResponse
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.AuthenticationDraftRequest
import com.srmanager.core.network.model.LoginRequest
import com.srmanager.core.network.model.NewPasswordRequest
import com.srmanager.core.network.model.RegistrationRequest
import com.srmanager.core.network.model.ResetPasswordRequest
import com.srmanager.core.network.model.UserWaitingRequest


interface AuthRepository {
    suspend fun getRegistrationResponse(registrationRequest: RegistrationRequest): Result<RegistrationResponse>
    suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginResponse>
    suspend fun getVerifyTokenResponse(token: String): Result<CommonResponseDto>
    suspend fun resendVerificationEmail(email: String, forProfileUpdate: Boolean): Result<CommonResponseDto>
    suspend fun resetPasswordLinkSend(string: ResetPasswordRequest): Result<CommonResponseDto>
    suspend fun passwordReset(newPasswordRequest: NewPasswordRequest): Result<CommonResponseDto>
    suspend fun updateEmail(email : String): Result<LoginResponse>
    suspend fun authenticationDraftCheck(authenticationDraftRequest: AuthenticationDraftRequest): Result<AuthenticationDraftResponse>
    suspend fun verificationEmailDraft() : Result<CommonResponseDto>
    suspend fun draftUserTokenVerify(email: String,token: String): Result<LoginResponse>
}