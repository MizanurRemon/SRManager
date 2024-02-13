package com.srmanager.order_presentation.products

sealed class OrderProductsEvent {
    object OnNextEvent : OrderProductsEvent()
    data class OnIncrementEvent(val id: Int) : OrderProductsEvent()
    data class OnDecrementEvent(val id: Int) : OrderProductsEvent()
    data class OnItemClickEvent(val id: Int, val isSelected: Boolean): OrderProductsEvent()
}