package com.srmanager.auth.auth_domain.model

data class RegistrationModel(
    val ageCategory: String,
    val confirmPassword: String,
    val email: String,
    val isAgreedToTermsAndConditions: Boolean,
    val language: String,
    val password: String,
)