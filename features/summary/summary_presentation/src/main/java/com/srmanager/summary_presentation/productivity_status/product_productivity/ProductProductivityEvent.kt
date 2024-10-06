package com.srmanager.summary_presentation.productivity_status.product_productivity

sealed class ProductProductivityEvent {
    data class OnMonthSelectionDialogOpen(val isOpened: Boolean) : ProductProductivityEvent()
    data class OnMonthSelection(val selectedMonth: Pair<String, String>) : ProductProductivityEvent()
    data class OnSearchEvent(val searchKey: String): ProductProductivityEvent()
    data class OnFilterApply(val filterItem: String): ProductProductivityEvent()
}