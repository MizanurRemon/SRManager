package com.srmanager.outlet_domain.use_cases

import com.srmanager.outlet_domain.model.OutletDetailsResponse
import com.srmanager.outlet_domain.repository.OutletRepository

class OutletDetailsUseCases(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(outletID: String): Result<OutletDetailsResponse> {
        return outletRepository.getOutletDetails(outletID = outletID)
    }
}