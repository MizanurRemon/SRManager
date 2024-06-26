package com.srmanager.order_domain.use_case

import com.srmanager.order_domain.model.ProductsResponse
import com.srmanager.order_domain.repository.OrderRepository

class ProductsUseCases(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(customerId: String): Result<ProductsResponse> {
        return orderRepository.getProducts(customerId = customerId)
    }
}