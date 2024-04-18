package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable


@Serializable
data class OutletDataDto(
    var data: ArrayList<Outlet> = arrayListOf()
)


@Immutable
@Serializable
data class Outlet(
    var id: Int = 0,
    var customerId: Int = 0,
    var outletName: String = "",
    var address: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var mobileNo: String = "",
    var outletImage: String = "",
    var ownerName: String = "",
    var billingAddress : String? =""
)
