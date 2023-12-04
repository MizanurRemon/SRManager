package com.srmanager.outlet_presentation.outlet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.srmanager.core.common.util.UiEvent
import com.srmanager.outlet_domain.model.OutletResponse
import com.srmanager.outlet_presentation.outlet_add.OutletAddState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class OutletViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(OutletState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        getOutletList()
    }

    private fun getOutletList() {
        state = state.copy(outletList = OUTLET_LIST)
    }

}

val OUTLET_LIST = listOf(
    OutletResponse(
        outletName = "outlet name",
        ownerName = "owner name",
        dateOfBirth = "17/11/2023",
        mobileNo = "+60162229232",
        secondaryMobileNo = "+60162229235",
        tradeLicense = "45A89",
        expiryDate = "25/12/2031",
        vat = "450",
        address = "Dhaka, Bangladesh",
        latitude = "23.045789",
        longitude = "90.165164"
    )
)

