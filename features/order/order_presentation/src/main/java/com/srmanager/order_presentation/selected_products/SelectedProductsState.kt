package com.srmanager.order_presentation.selected_products

import com.srmanager.core.network.dto.Product

data class SelectedProductsState(
    var productsList: List<Product> = arrayListOf(),
)
