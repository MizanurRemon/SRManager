package com.srmanager.summary_presentation.activity_details

import com.srmanager.core.common.R as CommonR
import java.time.LocalDate

data class ActivitiesDetailsState (
    val isLoading: Boolean = false,
    val isMonthSelectionDialogOpen: Boolean = false,
    val selectedMonth: Pair<String, String> = Pair(
        LocalDate.now().month.name,
        LocalDate.now().year.toString()
    ),
    val salesmanName : String = "Remon",
    val salesmanCode: String = "156156",
    val filterList: List<Int> = listOf(
        CommonR.string.all,
        CommonR.string.visited,
        CommonR.string.not_visited,
        CommonR.string.ordered
    ),
    val selectedFilterItem : Int = CommonR.string.all,
    val isFilterDialogOpen: Boolean = false
)