package com.srmanager.auth.auth_domain.use_cases

import com.srmanager.core.common.domain.use_case.EmailValidationResult
import com.srmanager.core.common.domain.use_case.PasswordValidationResult

data class AuthUseCases(
    val emailValidate: EmailValidationResult,
    val passwordValidate: PasswordValidationResult,
    val loginUseCase: LoginUseCase,
)