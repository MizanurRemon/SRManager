package com.srmanager.outlet_presentation.outlet

sealed class OutletEvent {
    object OnRefreshEvent: OutletEvent()
    data class OnSearchEvent(val value: String): OutletEvent()

}