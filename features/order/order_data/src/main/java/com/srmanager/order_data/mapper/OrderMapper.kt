package com.srmanager.order_data.mapper

import com.srmanager.core.common.model.CommonResponse
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.OrderDataDto
import com.srmanager.core.network.dto.OrderDetailsDto
import com.srmanager.core.network.dto.ProductsDto
import com.srmanager.order_domain.model.OrderDetailsResponse
import com.srmanager.order_domain.model.OrderItemResponse
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

fun OrderDetailsDto.toResponse(): OrderDetailsResponse {
    return OrderDetailsResponse(
        id = id ?: 0,
        orderNo = orderNo ?: "",
        orderDate = orderDate ?: "",
        outletAddress = outletAddress ?: "",
        billingAddress = billingAddress ?: "",
        salesMan = salesMan ?: "",
        salesManMobile = salesManMobile ?: "",
        customerCode = customerCode ?: "",
        customerName = customerName ?: "",
        paymentType = paymentType ?: "",
        inWords = inWords ?: "",
        signature = signature ?: "",
        contactNo = contactNo?: "",
        data = data.map { productItem ->
            OrderItemResponse(
                productCode = productItem.productCode ?: "",
                productName = productItem.productName ?: "",
                unit = productItem.unit ?: "",
                quantity = productItem.quantity ?: 0,
                mrp = productItem.mrp ?: 0.0,
                price = productItem.price ?: 0.0,
                discountAmount = productItem.discountAmount ?: 0,
                discountPercentage = productItem.discountPercentage ?: 0,
                afterDiscount = productItem.afterDiscount ?: 0.0,
                vatAmount = productItem.vatAmount ?: 0,
                netAmount = productItem.netAmount ?: 0.0
            )
        }
    )
}