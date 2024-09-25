package com.srmanager.summary_presentation.activity_details

import java.time.LocalDate

data class ActivitiesDetailsState (
    val isLoading: Boolean = false,
    val isMonthSelectionDialogOpen: Boolean = false,
    val selectedMonth: Pair<String, String> = Pair(
        LocalDate.now().month.name,
        LocalDate.now().year.toString()
    ),
    val salesmanName : String = "Remon",
    val salesmanCode: String = "156156"
)