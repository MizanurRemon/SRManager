package com.srmanager.summary_presentation.activity_details

import android.content.Context

sealed class ActivitiesDetailsEvent {
    data class OnMonthSelectionDialogOpen(val isOpened: Boolean) : ActivitiesDetailsEvent()
    data class OnMonthSelection(val selectedMonth: Pair<String, String>): ActivitiesDetailsEvent()
    data class OnFilterDialogOpen(val isOpened: Boolean): ActivitiesDetailsEvent()
    data class OnFilterSelection(val selectedItem: Int, val context: Context): ActivitiesDetailsEvent()
    data class OnFilterApply(val filterItem: String): ActivitiesDetailsEvent()
}