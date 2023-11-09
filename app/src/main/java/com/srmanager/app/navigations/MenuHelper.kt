package com.srmanager.app.navigations

import com.srmanager.core.common.navigation.Route
import com.srmanager.core.designsystem.R as DesignSystemR

val NAVIGATION_ITEMS = listOf(
    NavigationItem(
        DesignSystemR.drawable.ic_home, Route.HOME, "Home"
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_customer_add, Route.CUSTOMER_ADD, "Customer dd"
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_cart, Route.ORDER, "Order"
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_analyze, Route.REPORT, "Report"
    ),
)