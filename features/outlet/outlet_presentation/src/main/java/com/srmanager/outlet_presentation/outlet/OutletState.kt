package com.srmanager.outlet_presentation.outlet

import com.srmanager.outlet_domain.model.OutletResponse

data class OutletState(
    var outletList: List<OutletResponse> = emptyList()
)
