package com.srmanager.summary_presentation.activity_summary

import com.srmanager.core.common.util.SUMMARY_FILTERED_ITEMS


data class ActivitySummaryState(
    val selectedItem: Int = SUMMARY_FILTERED_ITEMS[0],
    val isDropDownOpened: Boolean = false
)
