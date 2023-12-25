package com.srmanager.outlet_domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class OutletAddModel(
    var id: String = "",
    var outletImage: String? = null,
    var outletName: String? = null,
    var ownerName: String? = null,
    var dateOfBirth: String? = null,
    var mobileNo: String? = null,
    var secondaryMobileNo: String? = null,
    var tradeLicense: String? = null,
    var expiryDate: String? = null,
    var vat: String? = null,
    var address: String? = null,
    var latitude: String? = null,
    var longitude: String? = null
)
