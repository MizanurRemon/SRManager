package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserPoint(
    val point: Int,
    val rewardCode: String
)