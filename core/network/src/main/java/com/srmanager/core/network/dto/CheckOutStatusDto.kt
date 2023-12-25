package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
data class CheckOutStatusDto(
    var data: ArrayList<StatusResponse> = arrayListOf()
)

@Immutable
@Serializable
data class StatusResponse(
    var id: Int? = 0,
    var name: String = ""
)
