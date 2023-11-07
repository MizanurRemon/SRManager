package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class TokenDto(
    val access_token: String,
    val refresh_token: String,
)
