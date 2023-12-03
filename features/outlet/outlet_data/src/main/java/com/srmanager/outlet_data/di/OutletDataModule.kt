package com.srmanager.outlet_data.di

import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.di.TypeEnum
import com.srmanager.core.network.di.qualifier.PrivateNetwork
import com.srmanager.core.network.di.qualifier.PublicNetwork
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.outlet_data.dataSource.remote.OutletRemoteDataSource
import com.srmanager.outlet_data.dataSourceImpl.remote.OutletRemoteDataSourceImpl
import com.srmanager.outlet_data.repository.OutletRepositoryImpl
import com.srmanager.outlet_domain.repository.OutletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class OutletDataModule {
    @Singleton
    @Provides
    fun provideOutletRemoteDataSource(
        @PublicNetwork(TypeEnum.SERVICE) publicApiService: PublicApiService,
        @PrivateNetwork(TypeEnum.SERVICE) privateApiService: PrivateApiService,
    ): OutletRemoteDataSource {
        return OutletRemoteDataSourceImpl(publicApiService, privateApiService)
    }

    @Singleton
    @Provides
    fun provideOutletRepository(
        outletRemoteDataSource: OutletRemoteDataSource,
        networkHandler: NetworkHandler
    ): OutletRepository {
        return OutletRepositoryImpl(
            outletRemoteDataSource = outletRemoteDataSource,
            networkHandler = networkHandler
        )
    }
}