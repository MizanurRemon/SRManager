package com.srmanager.order_domain.use_case

import com.srmanager.order_domain.model.OrderDetailsResponse
import com.srmanager.order_domain.repository.OrderRepository

class OrderDetailsUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(orderID: String): Result<OrderDetailsResponse>{
        return orderRepository.orderDetails(orderId = orderID)
    }
}