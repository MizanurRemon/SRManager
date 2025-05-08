package com.srmanager.summary_presentation.activity_summary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.srmanager.core.common.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class ActivitySummaryViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ActivitySummaryState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {

    }

    fun onEvent(event: ActivitySummaryEvent) {
        when (event) {
            is ActivitySummaryEvent.OnItemSelection -> {
                state = state.copy(
                    selectedItem = event.selectedItem,
                    isDropDownOpened = false
                )
            }

            is ActivitySummaryEvent.OnFilterItemSelection -> {
                state = state.copy(
                    isDropDownOpened = event.isOpened
                )

            }

            is ActivitySummaryEvent.OnDateSelectionDialogOpen -> {
                state = state.copy(
                    isDateSelectionDialogOpen = event.isOpened
                )
            }

            is ActivitySummaryEvent.OnDateSelection -> {
                state = state.copy(
                    isDateSelectionDialogOpen = false,
                    startDate = event.fromDate,
                    endDate = event.toDate
                )
            }

            is ActivitySummaryEvent.OnMonthSelectionDialogOpen -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = event.isOpened
                )
            }

            is ActivitySummaryEvent.OnMonthSelection -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = false,
                    selectedMonth = event.selectedMonth
                )
            }

        }
    }

}