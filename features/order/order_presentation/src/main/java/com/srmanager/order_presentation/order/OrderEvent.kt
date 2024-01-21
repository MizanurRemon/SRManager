package com.srmanager.order_presentation.order

sealed class OrderEvent {
    object OnSubmitClick : OrderEvent()
}