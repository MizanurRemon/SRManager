package com.srmanager.order_presentation

sealed class OrderEvent {
    object OnSubmitClick : OrderEvent()
}