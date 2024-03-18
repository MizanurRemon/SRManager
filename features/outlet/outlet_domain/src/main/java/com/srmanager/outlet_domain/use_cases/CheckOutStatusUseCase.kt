package com.srmanager.outlet_domain.use_cases

import com.srmanager.outlet_domain.model.CheckOutStatusResponse
import com.srmanager.outlet_domain.repository.OutletRepository

class CheckOutStatusUseCase(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(): Result<CheckOutStatusResponse> {
        return outletRepository.getCheckOutStatusList()
    }
}