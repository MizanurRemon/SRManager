package com.srmanager.outlet_presentation.outlet_checkout

import com.srmanager.core.network.dto.StatusResponse

data class OutletCheckOutState(
    val selectedReason : String = "Select reason",
    val outletStatusId: String = "",
    val description: String = "",
    val isDescriptionEmpty : Boolean = false,
    val isReasonSelectionError: Boolean = false,
    val latitude: String = "",
    val longitude: String = "",
    val myLatitude: String = "",
    val myLongitude: String = "",
    val remainingWords: Int = 150,
    val textLimit: Int = 150,
    val isLoading : Boolean = false,
    val checkOutStatusList: List<StatusResponse> = emptyList(),
    var reasonItemClicked: Boolean = false,
    val isNetworkCalling: Boolean = false,
    val distance: Double = 0.0
)
