package com.srmanager.order_presentation.selected_products

sealed class SelectedProductEvent {
    object OnSubmitEvent : SelectedProductEvent()
    data class OnIncrementEvent(val id: Long) : SelectedProductEvent()
    data class OnDecrementEvent(val id: Long) : SelectedProductEvent()
}