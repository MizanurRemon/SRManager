package com.srmanager.order_domain.use_case

data class OrderUseCases(
    val productsUseCases: ProductsUseCases,
    val createOrderUseCases: CreateOrderUseCases,
    val orderFetchUseCases: OderFetchUseCases,
    val orderDetailsUseCase: OrderDetailsUseCase
)