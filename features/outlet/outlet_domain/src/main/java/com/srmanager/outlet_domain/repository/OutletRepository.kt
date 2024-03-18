package com.srmanager.outlet_domain.repository

import com.srmanager.core.network.model.CheckOutRequest
import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.outlet_domain.model.CheckOutStatusResponse
import com.srmanager.core.common.model.CommonResponse
import com.srmanager.outlet_domain.model.MarketResponse
import com.srmanager.outlet_domain.model.OutletDetailsResponse
import com.srmanager.outlet_domain.model.OutletResponse


interface OutletRepository {
    suspend fun addOutlet(outletAddRequest: OutletAddRequest): Result<CommonResponse>
    suspend fun getOutletList(): Result<OutletResponse>
    suspend fun getOutletDetails(outletID: String): Result<OutletDetailsResponse>
    suspend fun getCheckOutStatusList():Result<CheckOutStatusResponse>
    suspend fun checkout(checkOutRequest: CheckOutRequest): Result<CommonResponse>
    suspend fun getMarkets(): Result<MarketResponse>
}