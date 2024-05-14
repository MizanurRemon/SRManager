package com.srmanager.order_presentation.signature

import com.srmanager.core.network.dto.Product
import com.srmanager.order_domain.model.OrderDetailsResponse

data class SignatureState(
    val customerSign: String = "",
    var productsList: List<Product> = arrayListOf(),
    val total: Double = 0.0,
    val orderDate: String = "",
    val outletID: Int = 0,
    val isOrderReady: Boolean = false,
    val isLoading: Boolean = false,
    val contact: String = "",
    val orderSuccessDialog: Boolean = false,
    val orderDetails: OrderDetailsResponse = OrderDetailsResponse(
        id = 0,
        orderNo = "",
        orderDate = "",
        outletAddress = "",
        billingAddress = "",
        salesMan = "",
        salesManMobile = "",
        customerCode = "",
        customerName = "",
        paymentType = "",
        inWords = "",
        signature = "",
        data = emptyList(),
        contactNo = "",
        market = "",
        route = ""
    )
)
