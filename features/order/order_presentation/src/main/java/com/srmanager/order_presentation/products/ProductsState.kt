package com.srmanager.order_presentation.products

import com.srmanager.order_domain.model.Products

data class ProductsState(
    var productsList: List<Products> = emptyList(),
    var isLoading: Boolean = false
)