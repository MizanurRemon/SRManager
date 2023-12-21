package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class OutletDetailsDto(
    val data: OutletProfile? = OutletProfile()
)


@Serializable
data class OutletProfile(
    var id: Int = 0,
    var dateCreated: String= "",
    var unitInfoId: Int = 0,
    var expiryDate: String= "",
    var lastUpdated: String= "",
    var secondaryMobileNo: String= "",
    var outletStatusId: Int = 0,
    var updatedBy: Int = 0,
    var dateOfBirth: String= "",
    var tradeLicense: String= "",
    var domainStatusId: Int = 0,
    var latitude: String= "",
    var assignedTo: Int = 0,
    var address: String= "",
    var ownerName: String= "",
    var longitude: String= "",
    var mobileNo: String= "",
    var outletImage: String= "",
    var statusRemarks: String= "",
    var createdBy: Int = 0,
    var outletName: String= "",
    var lastVisited: String= "",
    var vat: String= ""
)
