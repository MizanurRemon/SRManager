package com.srmanager.core.common.di

import com.srmanager.core.common.domain.use_case.EmailValidationResult
import com.srmanager.core.common.domain.use_case.PasswordValidationResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    @Singleton
    fun provideEmailvalidateUseCase(): EmailValidationResult {
        return EmailValidationResult()
    }
    @Provides
    @Singleton
    fun providePasswordValidateUseCase(): PasswordValidationResult {
        return PasswordValidationResult()
    }
}