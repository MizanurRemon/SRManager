package com.srmanager.order_data.di

import android.content.Context
import com.srmanager.core.network.PrivateApiService
import com.srmanager.core.network.PublicApiService
import com.srmanager.core.network.di.TypeEnum
import com.srmanager.core.network.di.qualifier.PrivateNetwork
import com.srmanager.core.network.di.qualifier.PublicNetwork
import com.srmanager.core.network.util.NetworkHandler
import com.srmanager.database.dao.ProductsDao
import com.srmanager.order_data.dataSource.local.OrderLocalDataSource
import com.srmanager.order_data.dataSource.remote.OrderRemoteDataSource
import com.srmanager.order_data.dataSourceImpl.local.OrderLocalDataSourceImpl
import com.srmanager.order_data.dataSourceImpl.remote.OrderRemoteDataSourceImpl
import com.srmanager.order_data.repository.OrderRepositoryImpl
import com.srmanager.order_domain.repository.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class OrderDataModule {
    @Singleton
    @Provides
    fun provideOrderRemoteDataSource(
        @PublicNetwork(TypeEnum.SERVICE) publicApiService: PublicApiService,
        @PrivateNetwork(TypeEnum.SERVICE) privateApiService: PrivateApiService,
    ): OrderRemoteDataSource {
        return OrderRemoteDataSourceImpl(publicApiService, privateApiService)
    }

    @Singleton
    @Provides
    fun provideOrderLocalDataSource(productsDao: ProductsDao): OrderLocalDataSource {
        return OrderLocalDataSourceImpl(
            productsDao = productsDao
        )
    }


    @Singleton
    @Provides
    fun provideOrderRepository(
        orderRemoteDataSource: OrderRemoteDataSource,
        networkHandler: NetworkHandler,
        orderLocalDataSource: OrderLocalDataSource
    ): OrderRepository {
        return OrderRepositoryImpl(
            orderRemoteDataSource = orderRemoteDataSource,
            networkHandler = networkHandler,
            orderLocalDataSource = orderLocalDataSource
        )
    }
}