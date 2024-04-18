package com.srmanager.order_domain.model

import com.srmanager.core.network.dto.Order

data class OrderResponse(
    val data: List<Order> = emptyList()
)
