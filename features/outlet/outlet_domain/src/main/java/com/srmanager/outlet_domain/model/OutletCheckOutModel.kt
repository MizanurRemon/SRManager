package com.srmanager.outlet_domain.model

data class OutletCheckOutModel(
    val id: String,
    val outletStatusId: String,
    val statusRemarks: String,
    val latitude: String,
    val longitude: String
)
