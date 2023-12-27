package com.srmanager.app.location

data class LocationDetails(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null
)
