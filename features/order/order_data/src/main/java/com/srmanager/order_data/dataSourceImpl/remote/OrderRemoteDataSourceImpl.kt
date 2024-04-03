package com.srmanager.order_data.dataSourceImpl.remote

import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.OrderDataDto
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.order_data.dataSource.remote.OrderRemoteDataSource

class OrderRemoteDataSourceImpl(
    private val publicApiService: PublicApiService,
    private val privateApiService: PrivateApiService
) : OrderRemoteDataSource {
    override suspend fun getProducts(outletID: String): ProductsDto {
        return privateApiService.getProducts(outletID = outletID)
    }

    override suspend fun createOrder(orderRequest: OrderRequest): CommonResponseDto {
        return privateApiService.createOrder(request = orderRequest)
    }

    override suspend fun fetchOrders(): OrderDataDto {
        return privateApiService.fetchOrders()
    }
}