package com.srmanager.outlet_presentation.outlet_details

import com.srmanager.core.common.util.ETCHNICITIES
import com.srmanager.core.common.util.PAYMENT_OPTIONS
import com.srmanager.core.common.util.ROUTE_NAMES
import javax.annotation.concurrent.Immutable


@Immutable
data class OutletDetailsState(
    val id: Int = 0,
    val outletName: String = "",
    val ownerName: String = "",
    val birthdate: String = "",
    val phone1: String = "",
    val phone2: String = "",
    val tradeLicense: String = "",
    val tlcExpiryDate: String = "",
    val vatTRN: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val ethnicity: String = "",
    val paymentOption: String = "",
    val routeName: String = "",
    val marketName : String = "",
    val email : String = "",
    val isOutletNameError: Boolean = false,
    val isOwnerNameError: Boolean = false,
    val isBirthDateError: Boolean = false,
    val isPhone1Error: Boolean = false,
    val isPhone2Error: Boolean = false,
    val isTradeLicenseError: Boolean = false,
    val isVatTrnError: Boolean = false,
    val isExpiryDateError: Boolean = false,
    val isAddressError: Boolean = false,
    val isImageError: Boolean = false,
    val image: String = "",
    val isLoading : Boolean = false
)
