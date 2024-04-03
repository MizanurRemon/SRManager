package com.srmanager.order_domain.repository

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.dto.OrderDataDto
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.order_domain.model.OrderResponse
import com.srmanager.order_domain.model.ProductsResponse

interface OrderRepository {
    suspend fun getProducts(outletID: String): Result<ProductsResponse>
    suspend fun createOrder(orderRequest: OrderRequest): Result<CommonResponse>
     suspend fun fetchOrders(): Result<OrderResponse>
}