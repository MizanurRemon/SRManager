package com.srmanager.order_presentation.order

import com.srmanager.core.network.dto.Order
import com.srmanager.order_domain.model.OrderDetailsResponse
import java.text.SimpleDateFormat
import java.util.Date

data class OrderState(
    val isLoading: Boolean = false,
    val orderList: List<Order> = emptyList(),
    val searchedOrderList: List<Order> = emptyList(),
    val startDate: String = "",
    val endDate: String = "",
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
    ),
    val searchText : String = SimpleDateFormat("dd-MM-yyyy").format(Date()),
    val showCalenderDialog : Boolean = false
)
