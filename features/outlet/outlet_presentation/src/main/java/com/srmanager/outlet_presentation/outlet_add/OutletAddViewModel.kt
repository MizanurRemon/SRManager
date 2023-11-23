package com.srmanager.outlet_presentation.outlet_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OutletAddViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(OutletAddState())
        private set

    init {

    }

    fun onEvent(event: OutletAddEvent) {
        when (event) {
            is OutletAddEvent.OnAddButtonClick -> {
                state = state.copy(isShowEntryDialog = mutableStateOf(true))
            }

            is OutletAddEvent.OnSubmitButtonClick -> {

            }

            is OutletAddEvent.OnOutletNameEnter -> {
                state = state.copy(outletName = event.value)
            }

            is OutletAddEvent.OnOwnerNameEnter -> {
                state = state.copy(ownerName = event.value)
            }

            is OutletAddEvent.OnBirthDateEnter -> {
                state = state.copy(birthdate = event.value)
            }

            is OutletAddEvent.OnDatePick -> {
                state = state.copy(birthdate = event.value)
            }

            is OutletAddEvent.OnMobileNo1Enter -> {
                state = state.copy(phone1 = event.value)
            }

            is OutletAddEvent.OnMobileNo2Enter -> {
                state = state.copy(phone2 = event.value)
            }

            is OutletAddEvent.OnTradeLicenseEnter -> {
                state = state.copy(tradeLicense = event.value)
            }

            is OutletAddEvent.OnExpiryDateEnter -> {
                state = state.copy(tlcExpiryDate = event.value)
            }

            is OutletAddEvent.OnVatTRNEnter -> {
                state = state.copy(vatTRN = event.value)
            }

            is OutletAddEvent.OnAddressEnter-> {
                state = state.copy(address =  event.value)
            }
        }
    }
}