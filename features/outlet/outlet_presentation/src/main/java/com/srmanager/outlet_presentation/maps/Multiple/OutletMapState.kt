package com.srmanager.outlet_presentation.maps.Multiple

import com.srmanager.core.network.dto.Outlet

data class OutletMapState(
    var outletList: List<Outlet> = emptyList(),
    var isLoading: Boolean = false,
    var isFailed:Boolean = false,
    var myLatitude: Double = 0.0,
    var myLongitude : Double = 0.0,
    var isLocationFetched : Boolean = false

)
