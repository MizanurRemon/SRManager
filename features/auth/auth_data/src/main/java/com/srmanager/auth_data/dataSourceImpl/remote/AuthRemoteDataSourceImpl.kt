package com.srmanager.auth_data.dataSourceImpl.remote

import com.srmanager.auth_data.dataSource.remote.AuthRemoteDataSource
import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.dto.AuthenticationDraftDto
import com.srmanager.core.network.dto.RegistrationDto
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.LoginDto
import com.srmanager.core.network.dto.UserWaitingDto
import com.srmanager.core.network.model.*


class AuthRemoteDataSourceImpl(
    private val publicApiService: PublicApiService,
    private val privateApiService: PrivateApiService,
) : AuthRemoteDataSource {
    override suspend fun getRegistrationResponse(registrationRequest: RegistrationRequest): RegistrationDto {
        return publicApiService.registration(registrationRequest)
    }

    override suspend fun getLoginResponse(loginRequest: LoginRequest): LoginDto {
        return publicApiService.login(loginRequest)
    }

    override suspend fun getVerifyTokenResponse(token: String): CommonResponseDto {
        return publicApiService.tokenVerify(token)
    }

    override suspend fun resendVerificationEmail(resendVerification: ResendVerificationRequest): CommonResponseDto {
        return privateApiService.resendVerificationCode(resendVerification)
    }

    override suspend fun resetPasswordLinkSend(resetPasswordRequest: ResetPasswordRequest): CommonResponseDto {
        return publicApiService.resetPasswordLinkSend(resetPasswordRequest)
    }

    override suspend fun passwordReset(newPasswordRequest: NewPasswordRequest): CommonResponseDto {
        return publicApiService.passwordReset(newPasswordRequest)
    }


    override suspend fun updateEmail(updateEmailRequest: UpdateEmailRequest): LoginDto {
        return privateApiService.updateEmail(updateEmailRequest)
    }

    override suspend fun authenticationDraftCheck(authenticationDraftRequest: AuthenticationDraftRequest): AuthenticationDraftDto {
        return publicApiService.authenticationDraftCheck(authenticationDraftRequest)
    }

    override suspend fun verificationEmailDraft(draftVerificationRequest: DraftVerificationRequest): CommonResponseDto {
        return publicApiService.verificationEmailDraft(draftVerificationRequest)
    }

    override suspend fun draftUserTokenVerify(token: String): LoginDto {
        return publicApiService.draftUserTokenVerify(token)
    }

}