package com.srmanager.outlet_presentation.outlet_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.auth_presentation.isPhoneNumberValid
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.common.util.fileImageUriToBase64
import com.srmanager.database.dao.LocationDao
import com.srmanager.outlet_domain.model.OutletAddModel
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OutletDetailsViewModel @Inject constructor(
    private val locationDao: LocationDao,
    private val outletUseCases: OutletUseCases
) : ViewModel() {
    var state by mutableStateOf(OutletDetailsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getOutletDetails(outletID: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            outletUseCases.outletDetailsUseCases(outletID = outletID).onSuccess { response ->
                state = state.copy(
                    isLoading = false,
                    image = response.data.outletImage.toString(),
                    outletName = response.data.outletName.toString(),
                    ownerName = response.data.ownerName.toString(),
                    birthdate = response.data.dateOfBirth.toString(),
                    phone1 = response.data.mobileNo.toString(),
                    phone2 = response.data.secondaryMobileNo.toString(),
                    tradeLicense = response.data.tradeLicense.toString(),
                    tlcExpiryDate = response.data.expiryDate.toString(),
                    vatTRN = response.data.vat.toString(),
                    address = response.data.address.toString(),
                    latitude = response.data.latitude.toString(),
                    longitude = response.data.longitude.toString()
                )

            }.onFailure {
                state = state.copy(
                    isLoading = false
                )

                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.DynamicString(
                            it.message.toString()
                        )
                    )
                )
            }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            locationDao.getLocation().collect {
                if (it.isNotEmpty()) {
                    state = state.copy(
                        address = mutableStateOf(it[0].address.toString()).value,
                        latitude = it[0].latitude.toString(),
                        longitude = it[0].longitude.toString()
                    )

                }
            }
        }
    }

    fun onEvent(event: OutletDetailsEvent) {
        when (event) {


            is OutletDetailsEvent.OnSubmitButtonClick -> {
                viewModelScope.launch {
                    if (state.outletName.isEmpty() || state.ownerName.isEmpty() || state.birthdate.isEmpty() || state.phone1.isEmpty() || state.tradeLicense.isEmpty() || state.vatTRN.isEmpty() || state.image.isEmpty()) {
                        state = state.copy(
                            isOutletNameError = state.outletName.isEmpty(),
                            isOwnerNameError = state.ownerName.isEmpty(),
                            isBirthDateError = state.birthdate.isEmpty(),
                            isPhone1Error = state.phone1.isEmpty(),
                            isTradeLicenseError = state.tradeLicense.isEmpty(),
                            isVatTrnError = state.vatTRN.isEmpty(),
                            isImageError = state.image.isEmpty()
                        )
                    } else {


                        state = state.copy(
                            isOutletNameError = false,
                            isOwnerNameError = false,
                            isBirthDateError = false,
                            isPhone1Error = false,
                            isPhone2Error = false,
                            isTradeLicenseError = false,
                            isVatTrnError = false,
                            isImageError = false,
                            isLoading = true
                        )

                        //outlet update
                        outletUseCases.outletAddUseCase(
                            OutletAddModel(
                                id = event.outletID,
                                outletImage = state.image,
                                outletName = state.outletName,
                                ownerName = state.ownerName,
                                dateOfBirth = state.birthdate,
                                mobileNo = state.phone1,
                                secondaryMobileNo = state.phone2,
                                tradeLicense = state.tradeLicense,
                                expiryDate = state.tlcExpiryDate,
                                vat = state.vatTRN,
                                address = state.address,
                                latitude = state.latitude,
                                longitude = state.longitude
                            )
                        ).onSuccess {
                            state = state.copy(
                                isLoading = false
                            )
                            _uiEvent.send(
                                UiEvent.Success
                            )

                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        it.message
                                    )
                                )
                            )
                        }.onFailure {
                            state = state.copy(
                                isLoading = false
                            )

                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        it.message.toString()
                                    )
                                )
                            )
                        }

                    }
                }
            }

            is OutletDetailsEvent.OnOutletNameEnter -> {
                state =
                    state.copy(outletName = event.value, isOutletNameError = event.value.isEmpty())
            }

            is OutletDetailsEvent.OnOwnerNameEnter -> {
                state =
                    state.copy(ownerName = event.value, isOwnerNameError = event.value.isEmpty())
            }

            is OutletDetailsEvent.OnBirthDateEnter -> {
                state =
                    state.copy(birthdate = event.value, isBirthDateError = event.value.isEmpty())
            }

            is OutletDetailsEvent.OnDatePick -> {
                state =
                    state.copy(birthdate = event.value, isBirthDateError = event.value.isEmpty())
            }

            is OutletDetailsEvent.OnMobileNo1Enter -> {
                state = state.copy(
                    phone1 = event.value,
                    isPhone1Error = event.value.isEmpty() || !isPhoneNumberValid(event.value)
                )
            }

            is OutletDetailsEvent.OnMobileNo2Enter -> {
                state = state.copy(
                    phone2 = event.value,
                    isPhone2Error = event.value.isNotEmpty() && !isPhoneNumberValid(event.value)
                )
            }

            is OutletDetailsEvent.OnTradeLicenseEnter -> {
                state = state.copy(
                    tradeLicense = event.value,
                    isTradeLicenseError = event.value.isEmpty()
                )
            }

            is OutletDetailsEvent.OnExpiryDateEnter -> {
                state = state.copy(
                    tlcExpiryDate = event.value,
                    isExpiryDateError = event.value.isEmpty()
                )
            }

            is OutletDetailsEvent.OnVatTRNEnter -> {
                state = state.copy(vatTRN = event.value, isVatTrnError = event.value.isEmpty())
            }

            is OutletDetailsEvent.OnAddressEnter -> {
                state = state.copy(address = event.value)
            }

            is OutletDetailsEvent.OnImageSelection -> {
                state = state.copy(
                    image = fileImageUriToBase64(event.value, event.contentResolver),
                    isImageError = false
                )
            }

            is OutletDetailsEvent.OnGettingCurrentLocation -> {
                getCurrentLocation()
            }

        }
    }
}