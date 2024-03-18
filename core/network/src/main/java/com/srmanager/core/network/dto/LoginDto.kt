package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val data: String? = null,
    val httpStatus: Int,
    val message: String
)