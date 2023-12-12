package com.srmanager.outlet_domain.repository


import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.outlet_domain.model.CommonResponse
import com.srmanager.outlet_domain.model.OutletResponse


interface OutletRepository {
    suspend fun addOutlet(outletAddRequest: OutletAddRequest): Result<CommonResponse>
    suspend fun getOutletList(): Result<OutletResponse>
}