package com.srmanager.outlet_presentation.outlet_checkout

sealed class OutletCheckOutEvent {
    data class OnReasonSelect(val value: String) : OutletCheckOutEvent()
    data class OnRemarksEnter(val value: String) : OutletCheckOutEvent()
    object OnCardEvent: OutletCheckOutEvent()
    object OnSubmitClick: OutletCheckOutEvent()
}
