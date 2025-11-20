package com.srmanager.core.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class LoginDto(
    @SerializedName("data")
    val data: Data,
    @SerializedName("httpStatus")
    val httpStatus: Int,
    @SerializedName("message")
    val message: String
)


@Serializable
data class Data(
    @SerializedName("companyId")
    val companyId: Int,
    @SerializedName("token")
    val token: String
)