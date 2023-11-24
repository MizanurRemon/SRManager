package com.srmanager.outlet_presentation.outlet_add

sealed class OutletAddEvent {
    object OnSubmitButtonClick : OutletAddEvent()
    data class OnOutletNameEnter(val value: String): OutletAddEvent()
    data class OnOwnerNameEnter(val value: String): OutletAddEvent()
    data class OnBirthDateEnter(val value: String): OutletAddEvent()
    data class OnMobileNo1Enter(val value: String): OutletAddEvent()
    data class OnMobileNo2Enter(val value: String): OutletAddEvent()
    data class OnTradeLicenseEnter(val value: String): OutletAddEvent()
    data class OnVatTRNEnter(val value: String): OutletAddEvent()
    data class OnDatePick(val value : String): OutletAddEvent()
    data class OnExpiryDateEnter(val value: String): OutletAddEvent()
    data class OnAddressEnter(val value: String): OutletAddEvent()
}
