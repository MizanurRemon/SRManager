package com.srmanager.summary_presentation.productivity_status.outlet_productivity

import java.time.LocalDate

data class OutletProductivityState(
    val isLoading: Boolean = false,
    val isMonthSelectionDialogOpen: Boolean = false,
    val selectedMonth: Pair<String, String> = Pair(
        LocalDate.now().month.name,
        LocalDate.now().year.toString()
    ),
    val salesmanName: String = "Remon",
    val salesmanCode: String = "156156",
    val search: String = "",
    val visitingDetailsList: List<OutletProductivity> = emptyList(),
    val searchedVisitingList: List<OutletProductivity> = emptyList()
)


data class OutletProductivity(
    val outletCode: String,
    val outletName: String,
    val numberOfVisit: String,
    val numberOfOrder: String,
    val totalOrderAmount: String
)
