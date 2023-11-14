package com.srmanager.app.navigations

import com.srmanager.core.common.navigation.Route
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

val NAVIGATION_ITEMS = listOf(
    NavigationItem(
        DesignSystemR.drawable.ic_home, Route.HOME, CommonR.string.home
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_shop, Route.OUTLET, CommonR.string.outlet
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_cart, Route.ORDER, CommonR.string.order
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_analyze, Route.REPORT, CommonR.string.report
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_logout, Route.REPORT, CommonR.string.log_out
    ),
)