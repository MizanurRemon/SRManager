package com.srmanager.order_domain.use_case

import com.srmanager.order_domain.model.OrderResponse
import com.srmanager.order_domain.repository.OrderRepository

class OderFetchUseCases(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(): Result<OrderResponse> {
        return orderRepository.fetchOrders()
    }
}