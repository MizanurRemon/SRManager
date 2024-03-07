package com.srmanager.order_domain.use_case

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.order_domain.model.CreateOrderModel
import com.srmanager.order_domain.repository.OrderRepository

class CreateOrderUseCases(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(orderModel: CreateOrderModel): Result<CommonResponse> {
        return orderRepository.createOrder(
            orderRequest = OrderRequest(
                orderDetails = orderModel.orderDetails,
                orderInformation = orderModel.orderInformation
            )
        )
    }
}