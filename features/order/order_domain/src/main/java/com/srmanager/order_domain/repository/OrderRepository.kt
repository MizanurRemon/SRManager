package com.srmanager.order_domain.repository

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.order_domain.model.ProductsResponse

interface OrderRepository {
    suspend fun getProducts(): Result<ProductsResponse>
    suspend fun createOrder(orderRequest: OrderRequest): Result<CommonResponse>
}