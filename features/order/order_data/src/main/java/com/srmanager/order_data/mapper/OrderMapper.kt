package com.srmanager.order_data.mapper

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.order_domain.model.ProductsResponse

fun ProductsDto.toResponse(): ProductsResponse {
    return ProductsResponse(
        products = data
    )
}

fun CommonResponseDto.toResponse (): CommonResponse{
    return CommonResponse(
        message = message.toString()
    )
}