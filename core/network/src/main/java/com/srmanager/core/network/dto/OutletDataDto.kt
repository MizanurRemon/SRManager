package com.srmanager.core.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class OutletDataDto(
    var data: ArrayList<Data> = arrayListOf()
)


@Serializable
data class Data(
    var id: Int = 0,
    var outletName: String = "",
    var address: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var mobileNo: String = "",
    var outletImage: String = "",
    var ownerName: String = ""
)
