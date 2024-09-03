package com.srmanager.summary_presentation.activity_summary

sealed class ActivitySummaryEvent {
    data class OnItemSelection(val selectedItem: Int) : ActivitySummaryEvent()
    data class OnFilterItemSelection(val isOpened: Boolean) : ActivitySummaryEvent()
}