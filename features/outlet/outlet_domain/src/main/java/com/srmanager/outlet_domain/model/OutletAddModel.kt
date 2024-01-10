package com.srmanager.outlet_domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class OutletAddModel(
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
    var marketID: Int,
    val ethnicity: String,
    val email: String,
    val paymentOptions: String,
    var routeName: String
)
