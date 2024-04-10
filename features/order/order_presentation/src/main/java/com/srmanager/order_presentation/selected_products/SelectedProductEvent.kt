package com.srmanager.order_presentation.selected_products

sealed class SelectedProductEvent {
    object OnSubmitEvent : SelectedProductEvent()
    data class OnIncrementEvent(val id: Long, val selectedItemCount: Int) : SelectedProductEvent()
    data class OnDecrementEvent(val id: Long, val selectedItemCount: Int) : SelectedProductEvent()
}