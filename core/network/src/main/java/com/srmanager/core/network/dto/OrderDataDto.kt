package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class OrderDataDto(
    val data: List<Order> = emptyList()
)

@Serializable
data class Order(
    val id: Int? = null,
    val code: String? = null,
    val name: String? = null,
    val data: String?= null,
    val status: String? = null,
    val amount: String? = null,
    val type : String? = null,
)
