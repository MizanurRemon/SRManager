package com.srmanager.order_presentation.products

sealed class OrderProductsEvent {
    object OnNextEvent : OrderProductsEvent()
    data class OnIncrementEvent(val index: Int) : OrderProductsEvent()
    object OnDecrementEvent : OrderProductsEvent()
}