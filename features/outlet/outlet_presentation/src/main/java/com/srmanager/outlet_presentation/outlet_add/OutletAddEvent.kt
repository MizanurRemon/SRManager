package com.srmanager.outlet_presentation.outlet_add

import android.content.ContentResolver
import android.net.Uri

sealed class OutletAddEvent {
    object OnSubmitButtonClick : OutletAddEvent()
    data class OnOutletNameEnter(val value: String) : OutletAddEvent()
    data class OnOwnerNameEnter(val value: String) : OutletAddEvent()
    data class OnBirthDateEnter(val value: String) : OutletAddEvent()
    data class OnMobileNo1Enter(val value: String) : OutletAddEvent()
    data class OnMobileNo2Enter(val value: String) : OutletAddEvent()
    data class OnTradeLicenseEnter(val value: String) : OutletAddEvent()
    data class OnVatTRNEnter(val value: String) : OutletAddEvent()
    data class OnDatePick(val value: String) : OutletAddEvent()
    data class OnExpiryDateEnter(val value: String) : OutletAddEvent()
    data class OnAddressEnter(val value: String) : OutletAddEvent()
    data class OnBillingAddressEnter(val value: String) : OutletAddEvent()
    data class OnIsBillingAddressSameAsAddressEvent(val value: Boolean): OutletAddEvent()
    data class OnEmailEnter(val value: String): OutletAddEvent()
    data class OnImageSelection(val value: Uri, val contentResolver: ContentResolver) :
        OutletAddEvent()
    object OnEthnicityDropDownClick : OutletAddEvent()
    data class OnEthnicitySelection(val value: String): OutletAddEvent()
    data class OnPaymentOptionSelection(val value : String): OutletAddEvent()
    object OnPaymentDropDownClick: OutletAddEvent()
    object OnRouteNameDropDownClick: OutletAddEvent()
    data class OnRouteNameSelection(val value: String): OutletAddEvent()
    object OnMarketNameDropDownClick: OutletAddEvent()
    data class OnMarketNameSelection(val value: String): OutletAddEvent()
    data class OnRouteType(val value: String): OutletAddEvent()
}
