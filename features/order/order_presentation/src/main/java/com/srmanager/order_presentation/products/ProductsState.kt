package com.srmanager.order_presentation.products

import com.srmanager.core.network.dto.Product

data class ProductsState(
    var productsList: List<Product> = arrayListOf(),
    var isLoading: Boolean = false,
    var isNextButtonEnabled: Boolean = false,
    var searchKey : String = ""
)