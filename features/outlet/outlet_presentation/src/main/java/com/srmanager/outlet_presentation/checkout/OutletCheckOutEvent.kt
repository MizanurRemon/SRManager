package com.srmanager.outlet_presentation.checkout

sealed class OutletCheckOutEvent {
    data class OnReasonSelect(val value: String) : OutletCheckOutEvent()
    data class OnRemarksEnter(val value: String) : OutletCheckOutEvent()
}
