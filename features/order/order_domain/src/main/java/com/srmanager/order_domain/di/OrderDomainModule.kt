package com.srmanager.order_domain.di

import com.srmanager.order_domain.repository.OrderRepository
import com.srmanager.order_domain.use_case.CreateOrderUseCases
import com.srmanager.order_domain.use_case.OrderUseCases
import com.srmanager.order_domain.use_case.ProductsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@InstallIn(ViewModelComponent::class)
@Module
class OrderDomainModule {

    @ViewModelScoped
    @Provides
    fun provideOutletUseCases(
        orderRepository: OrderRepository,
    ): OrderUseCases {
        return OrderUseCases(
            productsUseCases = ProductsUseCases(orderRepository),
            createOrderUseCases = CreateOrderUseCases(orderRepository)
        )
    }
}