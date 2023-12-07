package com.srmanager.outlet_presentation.checkout

data class OutletCheckOutState(
    val description: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val remainingWords: Int = 150,
    val textLimit: Int = 150
)
