package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class OrderDetailsDto(
    val id: Long?,
    val orderNo: String?,
    val orderDate: String?,
    val outletAddress: String?,
    val billingAddress: String?,
    val salesMan: String?,
    val salesManMobile: String?,
    val customerCode: String?,
    val customerName: String?,
    val paymentType: String?,
    val inWords: String?,
    val signature: String?,
    val contactNo: String?,
    val data: List<OrderItem>
)


@Serializable
data class OrderItem(
    val productCode: String?,
    val productName: String?,
    val unit: String?,
    val quantity: Long?,
    val mrp: Double?,
    val price: Double?,
    val discountAmount: Long?,
    val discountPercentage: Long?,
    val afterDiscount: Double?,
    val vatAmount: Long?,
    val netAmount: Double?
)
