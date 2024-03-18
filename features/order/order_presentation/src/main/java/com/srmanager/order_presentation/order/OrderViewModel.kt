package com.srmanager.order_presentation.order

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.srmanager.core.common.util.DATE_FORMAT
import com.srmanager.core.common.util.UiEvent
import com.srmanager.order_domain.model.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(OrderState())
        private set

    init {
        state = state.copy(
            startDate = SimpleDateFormat(DATE_FORMAT).format(Date()),
            endDate = SimpleDateFormat(DATE_FORMAT).format(Date())

        )
        getOrderItem()
    }

    private fun getOrderItem() {
        state = state.copy(orderList = ORDERS)
    }

}

val ORDERS = listOf(
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Hirok Daakua",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        code = "fwvwv46464vs",
        date = "10 Jan 2024"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Hirok Daakua",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        code = "fwvwv46464vs",
        date = "10 Jan 2024"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    ),
    OrderItem(
        id = 0,
        orderCode = "ORDER123",
        name = "Order 1",
        status = "pending",
        amount = "450",
        type = "EOT",
        date = "10 Jan 2024",
        code = "fwvwv46464vs"
    )

)