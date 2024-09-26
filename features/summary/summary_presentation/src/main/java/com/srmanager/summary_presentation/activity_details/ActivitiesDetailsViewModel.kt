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
            filteredVisitingList = visitingDetailsList
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

            is ActivitiesDetailsEvent.OnFilterSelection -> {

                val filterItem = event.context.resources.getString(event.selectedItem)
                state = state.copy(
                    isFilterDialogOpen = false,
                    selectedFilterItem = event.selectedItem,
                    filteredVisitingList =
                    when {
                        filterItem.lowercase() == "all" -> visitingDetailsList
                        else -> visitingDetailsList.filter {
                            it.outletStatus.lowercase() == filterItem.lowercase()
                        }
                    }
                )
            }

            is ActivitiesDetailsEvent.OnFilterDialogOpen -> {
                state = state.copy(
                    isFilterDialogOpen = event.isOpened
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