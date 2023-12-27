package com.srmanager.outlet_domain.use_cases

import com.srmanager.outlet_domain.model.OutletResponse
import com.srmanager.outlet_domain.repository.OutletRepository


class OutletListUseCase(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(): Result<OutletResponse> {
        return outletRepository.getOutletList()
    }
}