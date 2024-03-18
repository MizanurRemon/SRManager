package com.srmanager.outlet_domain.use_cases

import com.srmanager.core.network.model.CheckOutRequest
import com.srmanager.core.common.model.CommonResponse
import com.srmanager.outlet_domain.model.OutletCheckOutModel
import com.srmanager.outlet_domain.repository.OutletRepository

class OutletCheckOutUseCase(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(outletCheckOutModel: OutletCheckOutModel): Result<CommonResponse> {
        return outletRepository.checkout(
            CheckOutRequest(
                id = outletCheckOutModel.id,
                outletStatusId = outletCheckOutModel.outletStatusId,
                statusRemarks = outletCheckOutModel.statusRemarks,
                latitude = outletCheckOutModel.latitude,
                longitude = outletCheckOutModel.longitude
            )
        )
    }
}