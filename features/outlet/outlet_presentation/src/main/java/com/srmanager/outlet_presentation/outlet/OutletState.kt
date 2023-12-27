package com.srmanager.outlet_presentation.outlet

import com.srmanager.core.network.dto.Outlet
import com.srmanager.outlet_domain.model.OutletResponse

data class OutletState(
    var outletList: List<Outlet> = emptyList(),
    var isLoading: Boolean = false,
    var searchKey : String = ""
)
