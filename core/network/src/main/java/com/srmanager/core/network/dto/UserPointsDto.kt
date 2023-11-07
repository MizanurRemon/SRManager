package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserPointsDto(
    val roleRankPolicy: RoleRankPolicy?,
    val userPoints: UserPoints
)