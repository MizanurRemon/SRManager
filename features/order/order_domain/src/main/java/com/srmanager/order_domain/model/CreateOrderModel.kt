package com.srmanager.order_domain.model

import com.srmanager.core.network.model.OrderDetail
import com.srmanager.core.network.model.OrderInformation

data class CreateOrderModel(
    val orderInformation: OrderInformation,
    val orderDetails: List<OrderDetail>
)
