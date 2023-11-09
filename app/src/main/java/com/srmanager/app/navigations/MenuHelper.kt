package com.srmanager.app.navigations

import com.srmanager.core.common.navigation.Route
import com.srmanager.core.designsystem.R as DesignSystemR
import com.srmanager.core.common.R as CommonR

val NAVIGATION_ITEMS = listOf(
    NavigationItem(
        DesignSystemR.drawable.ic_home, Route.HOME, CommonR.string.home
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_customer_add, Route.CUSTOMER_ADD, CommonR.string.customer_add
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_cart, Route.ORDER, CommonR.string.order
    ),
    NavigationItem(
        DesignSystemR.drawable.ic_analyze, Route.REPORT, CommonR.string.report
    ),
)