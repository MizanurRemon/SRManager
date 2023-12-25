package com.srmanager.core.network

import com.srmanager.core.network.dto.*
import com.srmanager.core.network.model.*
import retrofit2.http.*

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
}