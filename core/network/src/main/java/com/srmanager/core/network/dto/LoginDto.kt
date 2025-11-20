package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val data: Data,
    val httpStatus: Int,
    val message: String
)


@Serializable
data class Data(
    val companyId: Int,
    val token: String
)