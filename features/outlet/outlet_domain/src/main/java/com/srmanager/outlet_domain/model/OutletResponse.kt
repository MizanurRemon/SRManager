package com.srmanager.outlet_domain.model

import com.srmanager.core.network.dto.Outlet


data class OutletResponse(
    var data: ArrayList<Outlet> = arrayListOf()
)



