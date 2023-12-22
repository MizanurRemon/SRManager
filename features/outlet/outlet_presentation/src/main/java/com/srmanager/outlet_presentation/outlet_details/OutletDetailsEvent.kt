package com.srmanager.outlet_presentation.outlet_details

import android.content.ContentResolver
import android.net.Uri
import com.srmanager.core.network.dto.Data

sealed class OutletDetailsEvent {
    data class OnSubmitButtonClick(val outletID: String) : OutletDetailsEvent()
    data class OnOutletNameEnter(val value: String) : OutletDetailsEvent()
    data class OnOwnerNameEnter(val value: String) : OutletDetailsEvent()
    data class OnBirthDateEnter(val value: String) : OutletDetailsEvent()
    data class OnMobileNo1Enter(val value: String) : OutletDetailsEvent()
    data class OnMobileNo2Enter(val value: String) : OutletDetailsEvent()
    data class OnTradeLicenseEnter(val value: String) : OutletDetailsEvent()
    data class OnVatTRNEnter(val value: String) : OutletDetailsEvent()
    data class OnDatePick(val value: String) : OutletDetailsEvent()
    data class OnExpiryDateEnter(val value: String) : OutletDetailsEvent()
    data class OnAddressEnter(val value: String) : OutletDetailsEvent()
    data class OnImageSelection(val value: Uri, val contentResolver: ContentResolver) :
        OutletDetailsEvent()

    object OnGettingCurrentLocation: OutletDetailsEvent()
}
