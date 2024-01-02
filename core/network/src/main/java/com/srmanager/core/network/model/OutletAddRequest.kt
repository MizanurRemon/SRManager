package com.srmanager.core.network.model

import kotlinx.serialization.Serializable


@Serializable
data class OutletAddRequest(
    var id: String = "",
    var outletImage: String,
    var outletName: String,
    var ownerName: String,
    var dateOfBirth: String,
    var mobileNo: String,
    var secondaryMobileNo: String,
    var tradeLicense: String,
    var expiryDate: String,
    var vat: String,
    var address: String,
    var latitude: String,
    var longitude: String,
    var marketName: String,
    var ethnicity : String,
    var email : String,
    var routeName : String,
    var paymentOptions : String
)
