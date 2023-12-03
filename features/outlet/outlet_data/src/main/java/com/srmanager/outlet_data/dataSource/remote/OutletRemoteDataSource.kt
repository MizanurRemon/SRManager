package com.srmanager.outlet_data.dataSource.remote


import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.*


interface OutletRemoteDataSource {
    suspend fun addOutlet(outletAddRequest: OutletAddRequest): CommonResponseDto

}