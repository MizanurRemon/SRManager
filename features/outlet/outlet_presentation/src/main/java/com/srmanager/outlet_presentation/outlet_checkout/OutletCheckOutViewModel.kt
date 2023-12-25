package com.srmanager.outlet_presentation.outlet_checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.database.dao.LocationDao
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OutletCheckOutViewModel @Inject constructor(
    private val locationDao: LocationDao,
    private val outletUseCases: OutletUseCases
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var state by mutableStateOf(OutletCheckOutState())
        private set

    init {
        viewModelScope.launch() {
            launch {
                locationDao.getLocation().collect {
                    if (it.isNotEmpty()) {
                        state = state.copy(
                            latitude = it[0].latitude.toString(),
                            longitude = it[0].longitude.toString()
                        )
                    }
                }
            }

            launch {
                state = state.copy(
                    isLoading = true
                )

                outletUseCases.checkOutStatusUseCase().onSuccess {
                    state = state.copy(
                        isLoading = false,
                        checkOutStatusList = it.data
                    )
                }.onFailure {
                    state = state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: OutletCheckOutEvent) {

        when (event) {
            is OutletCheckOutEvent.OnReasonSelect -> {
                state = state.copy(selectedReason = event.value, reasonItemClicked = false)
            }

            is OutletCheckOutEvent.OnRemarksEnter -> {
                state = state.copy(
                    description = event.value,
                    remainingWords = state.textLimit - event.value.length
                )
            }

            is OutletCheckOutEvent.OnCardEvent -> {
                state = state.copy(reasonItemClicked = !state.reasonItemClicked)
            }

        }
    }

}