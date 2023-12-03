package com.srmanager.outlet_domain.repository

import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.outlet_domain.model.CommonResponse
import dagger.Provides
import dagger.hilt.android.scopes.ViewModelScoped


interface OutletRepository {
    suspend fun addOutlet(outletAddRequest: OutletAddRequest): Result<CommonResponse>
}