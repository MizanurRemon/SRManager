package com.srmanager.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonErrorModel(
    val timestamp: Long = 0,
    val status: Int = 0,
    val error: String? = null,
    val description: List<String> = emptyList<String>(),
    val message: String? = null
)
