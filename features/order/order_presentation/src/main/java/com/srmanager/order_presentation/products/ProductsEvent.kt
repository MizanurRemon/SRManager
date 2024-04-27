package com.srmanager.order_presentation.products

sealed class ProductsEvent {
    object OnNextEvent : ProductsEvent()
    data class OnIncrementEvent(val id: Long, val itemCount: Int) : ProductsEvent()
    data class OnDecrementEvent(val id: Long, val itemCount: Int) : ProductsEvent()
    data class OnItemClickEvent(val id: Long, val isSelected: Boolean) : ProductsEvent()
    data class OnSearchEvent(val key: String) : ProductsEvent()
    data class OnQuantityInput(val qty: Double, val id: Long) : ProductsEvent()
    data class OnSetOutletID(val id: String, val customerId: String): ProductsEvent()
    data class OnNumberInputEvent(val value: String): ProductsEvent()
}