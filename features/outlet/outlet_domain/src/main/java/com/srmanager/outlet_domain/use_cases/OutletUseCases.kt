package com.srmanager.outlet_domain.use_cases

data class OutletUseCases(
    val outletAddUseCase: OutletAddUseCase,
    val outletListUseCases: OutletListUseCase,
    val outletDetailsUseCases: OutletDetailsUseCases,
    val checkOutStatusUseCase: CheckOutStatusUseCase,
    val outletCheckOutUseCase: OutletCheckOutUseCase,
    val outletMarketUseCase: OutletMarketUseCase
)