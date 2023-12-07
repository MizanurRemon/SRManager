package com.srmanager.outlet_presentation.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.database.dao.LocationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OutletCheckOutViewModel @Inject constructor(
    private val locationDao: LocationDao
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
        }
    }

    fun onEvent(event: OutletCheckOutEvent) {

        when (event) {
            is OutletCheckOutEvent.OnReasonSelect -> {

            }

            is OutletCheckOutEvent.OnRemarksEnter -> {
                state = state.copy(
                    description = event.value,
                    remainingWords = state.textLimit - event.value.length
                )
            }

        }
    }

}