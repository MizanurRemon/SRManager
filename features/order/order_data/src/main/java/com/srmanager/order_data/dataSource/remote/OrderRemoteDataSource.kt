package com.srmanager.order_data.dataSource.remote

import com.srmanager.core.network.dto.ProductsDto

interface OrderRemoteDataSource {
    suspend fun getProducts(): ProductsDto
}