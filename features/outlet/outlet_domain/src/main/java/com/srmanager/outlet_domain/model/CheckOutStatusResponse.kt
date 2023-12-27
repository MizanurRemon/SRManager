package com.srmanager.outlet_domain.model

import com.srmanager.core.network.dto.StatusResponse

data class CheckOutStatusResponse(
    var data: ArrayList<StatusResponse> = arrayListOf()
)
