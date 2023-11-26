package com.srmanager.outlet_presentation.outlet_add

data class OutletAddState(
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
    val isOutletNameError: Boolean = false,
    val isOwnerNameError: Boolean = false,
    val isBirthDateError: Boolean = false,
    val isPhone1Error: Boolean = false,
    val isPhone2Error: Boolean = false,
    val isTradeLicenseError: Boolean = false,
    val isVatTrnError: Boolean = false,
    val isExpiryDateError : Boolean = false
)
