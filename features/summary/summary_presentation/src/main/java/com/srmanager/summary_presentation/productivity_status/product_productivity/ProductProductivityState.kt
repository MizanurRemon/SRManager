package com.srmanager.summary_presentation.productivity_status.product_productivity

import java.time.LocalDate

data class ProductProductivityState(
    val isLoading: Boolean = false,
    val isMonthSelectionDialogOpen: Boolean = false,
    val selectedMonth: Pair<String, String> = Pair(
        LocalDate.now().month.name,
        LocalDate.now().year.toString()
    ),
    val salesmanName: String = "Remon",
    val salesmanCode: String = "156156",
    val search: String = "",
    val visitingDetailsList: List<ProductProductivity> = emptyList(),
    val searchedVisitingList: List<ProductProductivity> = emptyList()
)

data class ProductProductivity(
    val outletCode: String,
    val outletName: String,
    val numberOfVisit: String,
    val numberOfOrder: String,
    val totalOrderAmount: String
)
