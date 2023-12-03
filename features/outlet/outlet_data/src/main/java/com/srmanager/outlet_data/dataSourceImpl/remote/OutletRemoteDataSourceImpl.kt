package com.srmanager.outlet_data.dataSourceImpl.remote

import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.outlet_data.dataSource.remote.OutletRemoteDataSource

class OutletRemoteDataSourceImpl(
    private val publicApiService: PublicApiService,
    private val privateApiService: PrivateApiService
) : OutletRemoteDataSource {
    override suspend fun addOutlet(outletAddRequest: OutletAddRequest): CommonResponseDto {
        return publicApiService.addOutlet(request = outletAddRequest)
    }
}