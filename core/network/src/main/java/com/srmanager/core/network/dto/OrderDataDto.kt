package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class OrderDataDto(
    val data: List<Order> = emptyList()
)

@Serializable
data class Order(
    val id: Long,
    val outletName: String,
    val orderNo: String,
    val customerName: String,
    val orderDate: String,
    val orderAmount: Double,
    val orderStatus: String,
)
