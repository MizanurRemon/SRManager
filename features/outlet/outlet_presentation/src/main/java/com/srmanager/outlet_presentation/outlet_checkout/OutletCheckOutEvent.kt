package com.srmanager.outlet_presentation.outlet_checkout

import com.srmanager.core.network.dto.StatusResponse

sealed class OutletCheckOutEvent {
    data class OnReasonSelect(val value: StatusResponse) : OutletCheckOutEvent()
    data class OnRemarksEnter(val value: String) : OutletCheckOutEvent()
    object OnCardEvent: OutletCheckOutEvent()
    data class OnSubmitClick(val outletID: String): OutletCheckOutEvent()
    data class OnOutletLocationSetUp(val latitude: String, val longitude: String): OutletCheckOutEvent()
}
