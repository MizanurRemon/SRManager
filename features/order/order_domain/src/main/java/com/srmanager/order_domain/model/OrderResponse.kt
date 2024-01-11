package com.srmanager.order_domain.model

import com.srmanager.core.network.dto.Order

data class OrderResponse(
    val data: List<OrderItem> = emptyList()
)

data class OrderItem(
    val id: Int,
    val orderCode : String,
    val code: String,
    val name: String,
    val date: String,
    val status: String,
    val amount: String,
    val type : String
)
