package com.srmanager.outlet_presentation.outlet_add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.outlet_domain.use_cases.OutletUseCases
import com.srmanager.auth_presentation.isPhoneNumberValid
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.common.util.fileImageUriToBase64
import com.srmanager.database.dao.LocationDao
import com.srmanager.outlet_domain.model.OutletAddModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OutletAddViewModel @Inject constructor(
    private val locationDao: LocationDao,
    private val outletUseCases: OutletUseCases
) : ViewModel() {
    var state by mutableStateOf(OutletAddState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        viewModelScope.launch {
            launch {
                locationDao.getLocation().collect {
                    if (it.isNotEmpty()) {
                        state = state.copy(
                            address = it[0].address.toString(),
                            latitude = it[0].latitude.toString(),
                            longitude = it[0].longitude.toString()
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: OutletAddEvent) {
        when (event) {


            is OutletAddEvent.OnSubmitButtonClick -> {
                viewModelScope.launch {
                    state = state.copy(isLoading = true)
                    if (state.outletName.isEmpty() || state.ownerName.isEmpty() || state.birthdate.isEmpty() || state.phone1.isEmpty() || state.tradeLicense.isEmpty() || state.vatTRN.isEmpty() || state.image.isEmpty()) {
                        state = state.copy(
                            isOutletNameError = state.outletName.isEmpty(),
                            isOwnerNameError = state.ownerName.isEmpty(),
                            isBirthDateError = state.birthdate.isEmpty(),
                            isPhone1Error = state.phone1.isEmpty(),
                            isTradeLicenseError = state.tradeLicense.isEmpty(),
                            isVatTrnError = state.vatTRN.isEmpty(),
                            isImageError = state.image.isEmpty(),
                            isLoading = false
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
                            isImageError = false
                        )


                        outletUseCases.outletAddUseCase(
                            OutletAddModel(
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
                                isLoading = false,
                                image = "",
                                outletName = "",
                                ownerName = "",
                                birthdate =  "",
                                phone1 =  "",
                                phone2 =  "",
                                tradeLicense =  "",
                                tlcExpiryDate =  "",
                                vatTRN =  "",
                                address =  ""
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
                            state = state.copy(isLoading = false)
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

            is OutletAddEvent.OnOutletNameEnter -> {
                state =
                    state.copy(outletName = event.value, isOutletNameError = event.value.isEmpty())
            }

            is OutletAddEvent.OnOwnerNameEnter -> {
                state =
                    state.copy(ownerName = event.value, isOwnerNameError = event.value.isEmpty())
            }

            is OutletAddEvent.OnBirthDateEnter -> {
                state =
                    state.copy(birthdate = event.value, isBirthDateError = event.value.isEmpty())
            }

            is OutletAddEvent.OnDatePick -> {
                state =
                    state.copy(birthdate = event.value, isBirthDateError = event.value.isEmpty())
            }

            is OutletAddEvent.OnMobileNo1Enter -> {
                state = state.copy(
                    phone1 = event.value,
                    isPhone1Error = event.value.isEmpty() || !isPhoneNumberValid(event.value)
                )
            }

            is OutletAddEvent.OnMobileNo2Enter -> {
                state = state.copy(
                    phone2 = event.value,
                    isPhone2Error = event.value.isNotEmpty() && !isPhoneNumberValid(event.value)
                )
            }

            is OutletAddEvent.OnTradeLicenseEnter -> {
                state = state.copy(
                    tradeLicense = event.value,
                    isTradeLicenseError = event.value.isEmpty()
                )
            }

            is OutletAddEvent.OnExpiryDateEnter -> {
                state = state.copy(
                    tlcExpiryDate = event.value,
                    isExpiryDateError = event.value.isEmpty()
                )
            }

            is OutletAddEvent.OnVatTRNEnter -> {
                state = state.copy(vatTRN = event.value, isVatTrnError = event.value.isEmpty())
            }

            is OutletAddEvent.OnAddressEnter -> {
                state = state.copy(address = event.value)
            }

            is OutletAddEvent.OnImageSelection -> {
                state = state.copy(
                    image = fileImageUriToBase64(event.value, event.contentResolver),
                    isImageError = false
                )
            }

        }
    }
}