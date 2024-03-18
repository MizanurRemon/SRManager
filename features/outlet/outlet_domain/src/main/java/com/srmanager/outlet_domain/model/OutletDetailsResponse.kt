package com.srmanager.outlet_domain.model

import com.srmanager.core.network.dto.OutletProfile

data class OutletDetailsResponse(
    val data: OutletProfile = OutletProfile()
)
