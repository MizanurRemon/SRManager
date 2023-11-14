package com.srmanager.outlet_presentation.viewmodel

sealed class OutletEvent {
    object OnAddButtonClick : OutletEvent()
    object OnSubmitButtonClick : OutletEvent()
    data class OnOutletNameEnter(val name: String): OutletEvent()

    data class OnOwnerNameEnter(val name: String): OutletEvent()
    data class OnBirthDateEnter(val name: String): OutletEvent()
}
