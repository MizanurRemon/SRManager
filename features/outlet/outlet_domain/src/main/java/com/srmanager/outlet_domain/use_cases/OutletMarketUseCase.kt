package com.srmanager.outlet_domain.use_cases

import com.srmanager.outlet_domain.model.MarketResponse
import com.srmanager.outlet_domain.repository.OutletRepository

class OutletMarketUseCase(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(): Result<MarketResponse>{
        return outletRepository.getMarkets()
    }
}