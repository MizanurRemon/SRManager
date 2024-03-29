package com.srmanager.order_presentation.products

sealed class OrderProductsEvent {
    object OnNextEvent : OrderProductsEvent()
    data class OnIncrementEvent(val id: Long) : OrderProductsEvent()
    data class OnDecrementEvent(val id: Long) : OrderProductsEvent()
    data class OnItemClickEvent(val id: Long, val isSelected: Boolean): OrderProductsEvent()
}