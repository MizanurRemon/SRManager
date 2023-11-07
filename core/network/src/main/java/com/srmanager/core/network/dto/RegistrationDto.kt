package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationDto(
    val userId: Int?
)