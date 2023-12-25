package com.srmanager.outlet_presentation.outlet_checkout

import com.srmanager.core.network.dto.StatusResponse

data class OutletCheckOutState(
    val selectedReason : String = "Select reason",
    val description: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val remainingWords: Int = 150,
    val textLimit: Int = 150,
    val isLoading : Boolean = false,
    val checkOutStatusList: List<StatusResponse> = emptyList(),
    var reasonItemClicked: Boolean = false
)
