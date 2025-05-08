package com.srmanager.summary_presentation.activity_summary

import com.srmanager.core.common.util.SUMMARY_FILTERED_ITEMS
import com.srmanager.core.common.util.changeDateFormat
import com.srmanager.core.common.util.currentDate
import com.srmanager.core.common.util.currentMonth
import java.time.LocalDate


data class ActivitySummaryState(
    val selectedItem: Int = SUMMARY_FILTERED_ITEMS[0],
    val isDropDownOpened: Boolean = false,
    val startDate: String = changeDateFormat(
        currentDate(), "yyyy-MM-dd",
        "dd-MM-yyyy"
    ) ?: "",
    val endDate: String = changeDateFormat(
        currentDate(), "yyyy-MM-dd",
        "dd-MM-yyyy"
    ) ?: "",
    val isDateSelectionDialogOpen: Boolean = false,
    val isMonthSelectionDialogOpen: Boolean = false,
    val selectedMonth: Pair<String, String> = Pair(
        LocalDate.now().month.name,
        LocalDate.now().year.toString()
    ),
    val salesManName: String = "Remom",
    val salesManCode : String = "5644"
)
