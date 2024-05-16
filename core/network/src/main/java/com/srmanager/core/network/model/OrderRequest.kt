package com.srmanager.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class OrderRequest(
    val orderInformation: OrderInformation,
    val orderDetails: List<OrderDetail>,
)


@Serializable
data class OrderInformation(
    val customerSignature: String,
    val outletId: Long,
    val orderDate: String,
    val totalAmount: Double,
    val contactNo: String
)


@Serializable
data class OrderDetail(
    val productId: Long,
    val quantity: Long,
    val mrp: Double,
    val totalProductAmount: Double,
)
