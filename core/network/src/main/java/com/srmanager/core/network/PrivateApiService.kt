package com.srmanager.core.network

import com.srmanager.core.network.dto.CheckOutStatusDto
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.MarketDto
import com.srmanager.core.network.dto.OrderDataDto
import com.srmanager.core.network.dto.OutletDataDto
import com.srmanager.core.network.dto.OutletDetailsDto
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.core.network.model.CheckOutRequest
import com.srmanager.core.network.model.OrderRequest
import com.srmanager.core.network.model.OutletAddRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PrivateApiService {

    @POST("bsol/api/outlet")
    suspend fun addOutlet(@Body request: OutletAddRequest): CommonResponseDto

    @GET("bsol/api/outlet")
    suspend fun getOutletList(): OutletDataDto

    @GET("bsol/api/outlet/{outletID}")
    suspend fun getOutletDetails(
        @Path("outletID") outletID: String,
    ): OutletDetailsDto

    @GET("bsol/api/outlet/status")
    suspend fun getCheckOutStatusList(): CheckOutStatusDto

    @POST("bsol/api/outlet/checkout")
    suspend fun checkout(@Body request: CheckOutRequest): CommonResponseDto

    @POST("bsol/api/outlet/market")
    suspend fun getMarkets(): MarketDto

    @GET("bsol/api/products")
    suspend fun getProducts(
        @Query("outletId") outletID: String
    ): ProductsDto

    @POST("bsol/api/order")
    suspend fun createOrder(@Body request: OrderRequest): CommonResponseDto

    @GET("bsol/api/order")
    suspend fun fetchOrders(): OrderDataDto
}