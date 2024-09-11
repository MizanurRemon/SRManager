package com.srmanager.summary_presentation.activity_summary

sealed class ActivitySummaryEvent {
    data class OnItemSelection(val selectedItem: Int) : ActivitySummaryEvent()
    data class OnFilterItemSelection(val isOpened: Boolean) : ActivitySummaryEvent()
    data class OnDateSelectionDialogOpen(val isOpened: Boolean) : ActivitySummaryEvent()
    data class OnDateSelection(val fromDate: String, val toDate: String) : ActivitySummaryEvent()
    data class OnMonthSelectionDialogOpen(val isOpened: Boolean) : ActivitySummaryEvent()
    data class OnMonthSelection(val selectedMonth: Pair<String, String>) :
        ActivitySummaryEvent()
}