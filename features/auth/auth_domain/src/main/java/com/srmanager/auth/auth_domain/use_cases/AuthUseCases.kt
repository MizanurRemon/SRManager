package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.core.common.domain.use_case.EmailValidationResult
import com.srmanager.core.common.domain.use_case.PasswordValidationResult


data class AuthUseCases(
    val postRegistrationData: RegistrationUseCase,
    val emailValidate: EmailValidationResult,
    val passwordValidate: PasswordValidationResult,
    val verifyTokenUseCase: VerifyTokenUseCase,
    val resendVerificationEmilUseCase: ResendVerificationEmilUseCase,
    val loginUseCase: LoginUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val resetPasswordLinkSendUseCase: ResetPasswordLinkSendUseCase,
    val updateEmailUseCase: UpdateEmailUseCase,
    val authenticationDraftUseCase: AuthenticationDraftUseCase,
    val draftVerificationUseCases : DraftVerificationUseCases,
    val draftUserTokenVerifyUseCase: DraftUserTokenVerifyUseCases
)