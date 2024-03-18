package com.srmanager.outlet_presentation.dashboard

import com.srmanager.core.common.navigation.Route
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

val OUTLET_DASHBOARD_MENUS = listOf(
    DashboardItemsResponse(
        icon = DesignSystemR.drawable.ic_check,
        title = CommonR.string.check_out,
        route = Route.OUTLET_CHECKOUT
    ),

    DashboardItemsResponse(
        icon = DesignSystemR.drawable.ic_details,
        title = CommonR.string.outlet_details,
        route = Route.OUTLET_DETAILS
    ),
    DashboardItemsResponse(
        icon = DesignSystemR.drawable.ic_analyze,
        title = CommonR.string.report,
        route = Route.REPORT
    ),

    DashboardItemsResponse(
        icon = DesignSystemR.drawable.ic_cart,
        title = CommonR.string.order,
        route = Route.ORDER
    ),

    DashboardItemsResponse(
        icon = DesignSystemR.drawable.ic_location_pin,
        title = CommonR.string.location,
        route = Route.MAP
    )
)