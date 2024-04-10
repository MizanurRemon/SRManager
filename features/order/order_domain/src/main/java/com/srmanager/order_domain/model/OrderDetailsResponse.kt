package com.srmanager.order_domain.model

import com.srmanager.core.network.dto.OrderItem

data class OrderDetailsResponse(
    var data: List<OrderItem>
)
