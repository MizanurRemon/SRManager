package com.srmanager.summary_presentation.productivity_status.outlet_productivity

sealed class OutletProductivityEvent {
    data class OnMonthSelectionDialogOpen(val isOpened: Boolean) : OutletProductivityEvent()
    data class OnMonthSelection(val selectedMonth: Pair<String, String>) : OutletProductivityEvent()
    data class OnSearchEvent(val searchKey: String): OutletProductivityEvent()
    data class OnFilterApply(val filterItem: String): OutletProductivityEvent()
}