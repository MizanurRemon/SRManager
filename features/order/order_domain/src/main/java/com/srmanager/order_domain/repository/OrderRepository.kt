package com.srmanager.order_domain.repository

import com.srmanager.order_domain.model.ProductsResponse

interface OrderRepository {
    suspend fun getProducts(): Result<ProductsResponse>
}