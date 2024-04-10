package com.srmanager.order_data.mapper

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.OrderDataDto
import com.srmanager.core.network.dto.OrderDetailsDto
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.order_domain.model.OrderDetailsResponse
import com.srmanager.order_domain.model.OrderResponse
import com.srmanager.order_domain.model.ProductsResponse

fun ProductsDto.toResponse(): ProductsResponse {
    return ProductsResponse(
        products = data
    )
}

fun CommonResponseDto.toResponse(): CommonResponse {
    return CommonResponse(
        message = message ?: "",
        orderId = orderId ?: 0
    )
}

fun OrderDataDto.toResponse(): OrderResponse {
    return OrderResponse(
        data = data
    )
}

fun OrderDetailsDto.toResponse(): OrderDetailsResponse{
    return OrderDetailsResponse(
        data = data
    )
}