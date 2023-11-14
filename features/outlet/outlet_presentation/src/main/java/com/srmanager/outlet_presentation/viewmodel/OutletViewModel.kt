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
                state = state.copy(outletName = event.name)
            }

            is OutletEvent.OnOwnerNameEnter -> {
                state = state.copy(ownerName = event.name)
            }

            is OutletEvent.OnBirthDateEnter -> {
                state = state.copy(birthdate = event.name)
            }
        }
    }
}