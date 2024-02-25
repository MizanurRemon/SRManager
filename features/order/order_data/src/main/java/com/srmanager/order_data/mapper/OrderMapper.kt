package com.srmanager.order_data.mapper

import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.order_domain.model.ProductsResponse

fun ProductsDto.toResponse(): ProductsResponse {
    return ProductsResponse(
        products = data
    )
}