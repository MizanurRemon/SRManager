package com.srmanager.order_data.dataSource.remote

import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.core.network.model.OrderRequest

interface OrderRemoteDataSource {
    suspend fun getProducts(): ProductsDto
    suspend fun createOrder(orderRequest : OrderRequest): CommonResponseDto
}