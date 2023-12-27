package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class OutletDetailsDto(
    val data: OutletProfile? = OutletProfile()
)


@Serializable
data class OutletProfile(
    var id: Int? = null,
    var dateCreated: String? = null,
    var unitInfoId: Int? = null,
    var expiryDate: String? = null,
    var lastUpdated: String? = null,
    var secondaryMobileNo: String? = null,
    var outletStatusId: Int? = null,
    var updatedBy: Int? = null,
    var dateOfBirth: String? = null,
    var tradeLicense: String? = null,
    var domainStatusId: Int? = null,
    var latitude: String? = null,
    var assignedTo: Int? = null,
    var address: String? = null,
    var ownerName: String? = null,
    var longitude: String? = null,
    var mobileNo: String? = null,
    var outletImage: String? = null,
    var statusRemarks: String? = null,
    var createdBy: Int? = null,
    var outletName: String? = null,
    var lastVisited: String? = null,
    var vat: String? = null
)
