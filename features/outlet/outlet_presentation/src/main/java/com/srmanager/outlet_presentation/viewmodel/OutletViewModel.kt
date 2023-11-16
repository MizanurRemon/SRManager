package com.srmanager.outlet_presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OutletViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(OutletState())
        private set

    fun onEvent(event: OutletEvent) {
        when (event) {
            is OutletEvent.OnAddButtonClick -> {
                state = state.copy(isShowEntryDialog = mutableStateOf(true))
            }

            is OutletEvent.OnSubmitButtonClick -> {

            }

            is OutletEvent.OnOutletNameEnter -> {
                state = state.copy(outletName = event.value)
            }

            is OutletEvent.OnOwnerNameEnter -> {
                state = state.copy(ownerName = event.value)
            }

            is OutletEvent.OnBirthDateEnter -> {
                state = state.copy(birthdate = event.value)
            }

            is OutletEvent.OnDatePick -> {
                state = state.copy(birthdate = event.value)
            }

            is OutletEvent.OnMobileNo1Enter -> {
                state = state.copy(phone1 = event.value)
            }

            is OutletEvent.OnMobileNo2Enter -> {
                state = state.copy(phone2 = event.value)
            }

            is OutletEvent.OnTradeLicenseEnter-> {
                state = state.copy(tradeLicense = event.value)
            }
        }
    }
}