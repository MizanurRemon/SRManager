package com.srmanager.outlet_data.dataSource.remote


import com.srmanager.core.network.dto.CheckOutStatusDto
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.OutletDataDto
import com.srmanager.core.network.dto.OutletDetailsDto
import com.srmanager.core.network.model.*
import retrofit2.http.Body


interface OutletRemoteDataSource {
    suspend fun addOutlet(outletAddRequest: OutletAddRequest): CommonResponseDto
    suspend fun getOutletList(): OutletDataDto
    suspend fun getOutletDetails(outletID: String): OutletDetailsDto
    suspend fun getCheckOutStatusList(): CheckOutStatusDto
    suspend fun checkout(checkOutRequest: CheckOutRequest): CommonResponseDto
}