package com.srmanager.order_presentation.order

import com.srmanager.core.network.dto.Order
data class OrderState(
    val isLoading: Boolean = false,
    val orderList: List<Order> = emptyList(),
    val startDate: String = "",
    val endDate: String = ""
)
