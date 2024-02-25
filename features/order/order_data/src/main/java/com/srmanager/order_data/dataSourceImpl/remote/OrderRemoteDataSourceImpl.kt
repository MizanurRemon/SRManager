package com.srmanager.order_data.dataSourceImpl.remote

import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.order_data.dataSource.remote.OrderRemoteDataSource

class OrderRemoteDataSourceImpl(
    private val publicApiService: PublicApiService,
    private val privateApiService: PrivateApiService
) : OrderRemoteDataSource {
    override suspend fun getProducts(): ProductsDto {
        return privateApiService.getProducts()
    }
}