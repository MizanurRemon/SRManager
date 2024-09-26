package com.srmanager.summary_presentation.activity_details

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
class ActivitiesDetailsViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ActivitiesDetailsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        state = state.copy(
            searchedVisitingList = visitingDetailsList
        )
    }

    fun onEvent(event: ActivitiesDetailsEvent) {
        when (event) {
            is ActivitiesDetailsEvent.OnMonthSelectionDialogOpen -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = event.isOpened
                )
            }

            is ActivitiesDetailsEvent.OnMonthSelection -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = false,
                    selectedMonth = event.selectedMonth
                )
            }

            is ActivitiesDetailsEvent.OnSearchEvent -> {

                state = state.copy(
                    search = event.searchKey,
                    searchedVisitingList =
                    when {
                        event.searchKey.isEmpty() -> visitingDetailsList
                        else -> visitingDetailsList.filter {
                            it.outletCode.contains(event.searchKey, ignoreCase = true)
                                    || it.outletName.contains(event.searchKey, ignoreCase = true)
                                    || it.outletStatus.contains(event.searchKey, ignoreCase = true)
                        }
                    }
                )
            }

            is ActivitiesDetailsEvent.OnFilterApply -> {

            }
        }
    }
}

val visitingDetailsList = listOf(
    VisitingDetails(
        outletCode = "001",
        outletName = "Bismillah Traders",
        outletStatus = "Visited"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Ordered"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Ordered"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Ordered"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Ordered"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),

    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),
    VisitingDetails(
        outletCode = "005",
        outletName = "Haji Store",
        outletStatus = "Not Visited"
    ),
)