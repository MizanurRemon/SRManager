package com.srmanager.outlet_presentation.outlet_checkout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.MINIMUM_DISTANCE_FOR_CHECKOUT
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.common.util.calculationDistance
import com.srmanager.database.dao.LocationDao
import com.srmanager.outlet_domain.model.OutletCheckOutModel
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
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
        viewModelScope.launch {
           /* launch {
                *//*locationDao.getLocation().collect {
                    if (it.isNotEmpty()) {
                        state = state.copy(
                            myLatitude = it[0].latitude.toString(),
                            myLongitude = it[0].longitude.toString(),
                            distance = calculationDistance(
                                state.latitude,
                                state.longitude,
                                state.myLatitude,
                                state.myLongitude
                            )
                        )
                    }
                }*//*


                state = state.copy(
                    myLatitude = locationInfo[0].latitude.toString(),
                    myLongitude = locationInfo[0].longitude.toString(),
                    distance = calculationDistance(
                        state.latitude,
                        state.longitude,
                        locationInfo[0].latitude.toString(),
                        locationInfo[0].longitude.toString()
                    )
                )
            }*/

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
            is OutletCheckOutEvent.OnOutletLocationSetUp -> {
                viewModelScope.launch {
                    val locationInfo = locationDao.getLocation().first()
                    state = state.copy(
                        latitude = event.latitude,
                        longitude = event.longitude,
                        distance = calculationDistance(
                            state.latitude,
                            state.longitude,
                            locationInfo[0].latitude.toString(),
                            locationInfo[0].longitude.toString()
                        )
                    )
                }
            }

            is OutletCheckOutEvent.OnReasonSelect -> {
                state = state.copy(
                    selectedReason = event.value.name,
                    outletStatusId = event.value.id.toString(),
                    reasonItemClicked = false,
                    isReasonSelectionError = event.value.name == "Select reason"
                )
            }

            is OutletCheckOutEvent.OnRemarksEnter -> {
                state = state.copy(
                    description = event.value,
                    remainingWords = state.textLimit - event.value.length,
                    isDescriptionEmpty = event.value.isEmpty()
                )
            }

            is OutletCheckOutEvent.OnCardEvent -> {
                state = state.copy(reasonItemClicked = !state.reasonItemClicked)
            }

            is OutletCheckOutEvent.OnSubmitClick -> {
                viewModelScope.launch {
                    state = state.copy(
                        isDescriptionEmpty = state.description.isEmpty(),
                        isReasonSelectionError = state.selectedReason == "Select reason",
                    )

                    if (!state.isDescriptionEmpty && !state.isReasonSelectionError && state.distance <= MINIMUM_DISTANCE_FOR_CHECKOUT) {
                        state = state.copy(
                            isNetworkCalling = true
                        )


                        outletUseCases.outletCheckOutUseCase(
                            OutletCheckOutModel(
                                id = event.outletID,
                                outletStatusId = state.outletStatusId,
                                statusRemarks = state.description,
                                latitude = state.myLatitude,
                                longitude = state.myLongitude
                            )
                        ).onSuccess {
                            state = state.copy(
                                isNetworkCalling = false,
                                selectedReason = "Select reason",
                                description = ""
                            )
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        it.message
                                    )
                                )
                            )
                        }.onFailure {
                            state = state.copy(isNetworkCalling = false)
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        it.message.toString()
                                    )
                                )
                            )
                        }
                    } else if (state.distance > MINIMUM_DISTANCE_FOR_CHECKOUT) {
                        state = state.copy(
                            isNetworkCalling = false
                        )
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(
                                    "You are more than 300 m away from outlet"
                                )
                            )
                        )
                    }
                }
            }

        }
    }

}