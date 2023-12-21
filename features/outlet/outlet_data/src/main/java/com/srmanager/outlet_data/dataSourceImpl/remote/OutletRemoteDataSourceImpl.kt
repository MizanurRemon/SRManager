package com.srmanager.outlet_data.dataSourceImpl.remote

import com.srmanager.outlet_data.dataSource.remote.OutletRemoteDataSource
import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.OutletDataDto
import com.srmanager.core.network.dto.OutletDetailsDto
import com.srmanager.core.network.model.*


class OutletRemoteDataSourceImpl(
    private val publicApiService: PublicApiService,
    private val privateApiService: PrivateApiService,
) : OutletRemoteDataSource {
    override suspend fun addOutlet(outletAddRequest: OutletAddRequest): CommonResponseDto {
        return privateApiService.addOutlet(outletAddRequest)
    }

    override suspend fun getOutletList(): OutletDataDto {
        return privateApiService.getOutletList()
    }

    override suspend fun getOutletDetails(outletID: String): OutletDetailsDto{
        return privateApiService.getOutletDetails(outletID)
    }

}