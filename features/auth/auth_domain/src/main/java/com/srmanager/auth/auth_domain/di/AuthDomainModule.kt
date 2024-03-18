package com.srmanager.auth.auth_domain.di

import com.srmanager.auth.auth_domain.repository.AuthRepository
import com.srmanager.auth.auth_domain.use_cases.*
import com.srmanager.core.common.domain.use_case.EmailValidationResult
import com.srmanager.core.common.domain.use_case.PasswordValidationResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class AuthDomainModule {
    @ViewModelScoped
    @Provides
    fun provideAuthUseCases(
        authRepository: AuthRepository,
        emailValidate: EmailValidationResult,
        passwordValidate: PasswordValidationResult,
    ): AuthUseCases {
        return AuthUseCases(
            emailValidate = emailValidate,
            passwordValidate = passwordValidate,
            loginUseCase = LoginUseCase(authRepository)
        )
    }
}