package com.srmanager.summary_presentation.productivity_status.product_productivity

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
class ProductProductivityViewModel @Inject constructor(): ViewModel() {
    var state by mutableStateOf(ProductProductivityState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        state = state.copy(
            searchedVisitingList = productProductivityList
        )
    }

    fun onEvent(event: ProductProductivityEvent) {
        when (event) {
            is ProductProductivityEvent.OnMonthSelectionDialogOpen -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = event.isOpened
                )
            }

            is ProductProductivityEvent.OnMonthSelection -> {
                state = state.copy(
                    isMonthSelectionDialogOpen = false,
                    selectedMonth = event.selectedMonth
                )
            }

            is ProductProductivityEvent.OnSearchEvent -> {

                state = state.copy(
                    search = event.searchKey,
                    searchedVisitingList =
                    when {
                        event.searchKey.isEmpty() -> productProductivityList
                        else -> productProductivityList.filter {
                            it.outletCode.contains(event.searchKey, ignoreCase = true)
                                    || it.outletName.contains(event.searchKey, ignoreCase = true)
                                    || it.numberOfVisit.contains(event.searchKey, ignoreCase = true)
                        }
                    }
                )
            }

            is ProductProductivityEvent.OnFilterApply -> {

            }
        }
    }
}

val productProductivityList = listOf(
    ProductProductivity(
        outletCode = "001",
        outletName = "Bismillah Traders",
        numberOfVisit = "8",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "6",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "5",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "4",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),

    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
    ProductProductivity(
        outletCode = "005",
        outletName = "Haji Store",
        numberOfVisit = "3",
        numberOfOrder = "4",
        totalOrderAmount = "2000"
    ),
)