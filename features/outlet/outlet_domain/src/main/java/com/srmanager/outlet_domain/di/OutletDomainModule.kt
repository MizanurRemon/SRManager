package com.srmanager.outlet_domain.di

import com.srmanager.outlet_domain.repository.OutletRepository
import com.srmanager.outlet_domain.use_cases.CheckOutStatusUseCase
import com.srmanager.outlet_domain.use_cases.OutletAddUseCase
import com.srmanager.outlet_domain.use_cases.OutletCheckOutUseCase
import com.srmanager.outlet_domain.use_cases.OutletDetailsUseCases
import com.srmanager.outlet_domain.use_cases.OutletListUseCase
import com.srmanager.outlet_domain.use_cases.OutletMarketUseCase
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class OutletDomainModule {
    @ViewModelScoped
    @Provides
    fun provideOutletUseCases(
        outletRepository: OutletRepository,
    ): OutletUseCases {
        return OutletUseCases(
            outletAddUseCase = OutletAddUseCase(outletRepository),
            outletListUseCases = OutletListUseCase(outletRepository),
            outletDetailsUseCases = OutletDetailsUseCases(outletRepository),
            checkOutStatusUseCase = CheckOutStatusUseCase(outletRepository),
            outletCheckOutUseCase = OutletCheckOutUseCase(outletRepository),
            outletMarketUseCase = OutletMarketUseCase(outletRepository)
        )
    }
}