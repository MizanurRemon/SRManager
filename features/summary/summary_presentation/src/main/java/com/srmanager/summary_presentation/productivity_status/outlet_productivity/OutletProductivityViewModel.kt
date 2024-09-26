package com.srmanager.summary_presentation.productivity_status.outlet_productivity

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
class OutletProductivityViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(OutletProductivityState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        state = state.copy(
            searchedVisitingList = outletProductivityList
        )
    }

    fun onEvent(event: OutletProductivityEvent) {
        when (event) {
            is OutletProductivityEvent.OnMonthSelectionDialogOpen -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = event.isOpened
                )
            }

            is OutletProductivityEvent.OnMonthSelection -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = false,
                    selectedMonth = event.selectedMonth
                )
            }

            is OutletProductivityEvent.OnSearchEvent -> {

                state = state.copy(
                    search = event.searchKey,
                    searchedVisitingList =
                    when {
                        event.searchKey.isEmpty() -> outletProductivityList
                        else -> outletProductivityList.filter {
                            it.outletCode.contains(event.searchKey, ignoreCase = true)
                                    || it.outletName.contains(event.searchKey, ignoreCase = true)
                                    || it.numberOfVisit.contains(event.searchKey, ignoreCase = true)
                        }
                    }
                )
            }

            is OutletProductivityEvent.OnFilterApply -> {

            }
        }
    }
}

val outletProductivityList = listOf(
    OutletProductivity(
        outletCode = "001",
        outletName = "Bismillah Traders",
        numberOfVisit = "8",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "6",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "5",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "4",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    OutletProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
)