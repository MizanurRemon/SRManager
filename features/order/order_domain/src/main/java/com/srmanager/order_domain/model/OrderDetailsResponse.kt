package com.srmanager.order_domain.model

data class OrderDetailsResponse(
    val id: Long,
    val orderNo: String,
    val orderDate: String,
    val outletAddress: String,
    val billingAddress: String,
    val salesMan: String,
    val salesManMobile: String,
    val customerCode: String,
    val customerName: String,
    val paymentType: String,
    val inWords: String,
    val signature: String,
    val contactNo: String,
    var data: List<OrderItemResponse>
)

data class OrderItemResponse(
    val productCode: String,
    val productName: String,
    val unit: String,
    val quantity: Long,
    val mrp: Double,
    val price: Double,
    val discountAmount: Long,
    val discountPercentage: Long,
    val afterDiscount: Double,
    val vatAmount: Long,
    val netAmount: Double
)
