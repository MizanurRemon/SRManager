package com.srmanager.order_domain.model

import com.srmanager.core.network.dto.Product

data class ProductsResponse(
    var products: List<Product> = emptyList()
)
