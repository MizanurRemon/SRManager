package com.srmanager.outlet_presentation.outlet_details


import com.srmanager.core.network.dto.MarketItem
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
    val marketName: String = "",
    val email: String = "",
    val marketID: Int = 0,
    val billingAddress: String = "",
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
    val isLoading: Boolean = false,
    val isPaymentOptionsExpanded: Boolean = false,
    val isRouteNameExpanded: Boolean = false,
    val isMarketNameExpanded: Boolean = false,
    val marketNameList: List<MarketItem> = emptyList(),
    val isEmailError: Boolean = false,
    val isEthnicityError: Boolean = false,
    val isEthnicityExpanded: Boolean = false
)
