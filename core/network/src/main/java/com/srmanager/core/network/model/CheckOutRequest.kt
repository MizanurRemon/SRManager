package com.srmanager.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckOutRequest(
    val id: String,
    val outletStatusId: String,
    val statusRemarks: String,
    val latitude: String,
    val longitude: String
)
