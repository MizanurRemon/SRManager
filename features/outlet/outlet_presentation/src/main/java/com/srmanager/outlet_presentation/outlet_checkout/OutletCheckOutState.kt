package com.srmanager.outlet_presentation.outlet_checkout

import com.srmanager.core.network.dto.StatusResponse

data class OutletCheckOutState(
    val outletID : String="",
    val selectedReason : String = "Select reason",
    val outletStatusId: String = "",
    val description: String = "",
    val isDescriptionEmpty : Boolean = false,
    val isReasonSelectionError: Boolean = false,
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val myLatitude: String = "0.0",
    val myLongitude: String = "0.0",
    val remainingWords: Int = 150,
    val textLimit: Int = 150,
    val isLoading : Boolean = false,
    val checkOutStatusList: List<StatusResponse> = emptyList(),
    var reasonItemClicked: Boolean = false,
    val isNetworkCalling: Boolean = false,
    val distance: Double = 0.0
)
