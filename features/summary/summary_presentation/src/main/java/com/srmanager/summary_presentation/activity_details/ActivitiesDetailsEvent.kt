package com.srmanager.summary_presentation.activity_details

sealed class ActivitiesDetailsEvent {
    data class OnMonthSelectionDialogOpen(val isOpened: Boolean) : ActivitiesDetailsEvent()
    data class OnMonthSelection(val selectedMonth: Pair<String, String>): ActivitiesDetailsEvent()
    data class OnFilterDialogOpen(val isOpened: Boolean): ActivitiesDetailsEvent()
    data class OnFilterSelection(val selectedItem: Int): ActivitiesDetailsEvent()
}