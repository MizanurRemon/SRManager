package com.srmanager.order_presentation.order

import com.srmanager.order_domain.model.OrderItem

data class OrderState(
    val isLoading: Boolean = false,
    val orderList: List<OrderItem> = emptyList(),
    val startDate: String = "",
    val endDate: String = ""
)
