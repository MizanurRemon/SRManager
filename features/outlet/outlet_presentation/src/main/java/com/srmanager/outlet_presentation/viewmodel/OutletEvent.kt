package com.srmanager.outlet_presentation.viewmodel

sealed class OutletEvent {
    object OnAddButtonClick : OutletEvent()
    object OnSubmitButtonClick : OutletEvent()
    data class OnOutletNameEnter(val value: String): OutletEvent()
    data class OnOwnerNameEnter(val value: String): OutletEvent()
    data class OnBirthDateEnter(val value: String): OutletEvent()
    data class OnMobileNo1Enter(val value: String): OutletEvent()
    data class OnMobileNo2Enter(val value: String): OutletEvent()
    data class OnTradeLicenseEnter(val value: String): OutletEvent()
    data class OnDatePick(val value : String): OutletEvent()
}
