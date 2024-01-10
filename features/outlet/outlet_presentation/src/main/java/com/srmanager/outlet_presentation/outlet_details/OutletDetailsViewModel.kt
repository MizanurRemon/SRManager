package com.srmanager.outlet_presentation.outlet_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.auth_presentation.isEmailValid
import com.srmanager.auth_presentation.isPhoneNumberValid
import com.srmanager.core.common.util.ETCHNICITIES
import com.srmanager.core.common.util.MARKET_NAMES
import com.srmanager.core.common.util.PAYMENT_OPTIONS
import com.srmanager.core.common.util.ROUTE_NAMES
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
import java.util.Locale
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

    init {
        loadMarketNames()
    }

    private fun loadMarketNames() {
        viewModelScope.launch {
            outletUseCases.outletMarketUseCase().onSuccess {
                state = state.copy(
                    marketNameList = it.data
                )
            }.onFailure { }

        }
    }

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
                    longitude = response.data.longitude.toString(),
                    marketName = when (response.data.marketId) {
                        0 -> {
                            state.marketNameList.first().text.toString()
                        }
                        else -> {
                            state.marketNameList.first {
                                it.id == response.data.marketId
                            }.text.toString()
                        }
                    },
                    marketID = response.data.marketId!!.toInt(),
                    routeName = response.data.routeName!!.ifEmpty { ROUTE_NAMES[0] },
                    email = response.data.ownerEmail.toString(),
                    paymentOption = response.data.paymentTerms!!.ifEmpty { PAYMENT_OPTIONS[0] },
                    ethnicity = response.data.shopEthnicity!!.ifEmpty { ETCHNICITIES[0] }
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
                    state = state.copy(
                        isOutletNameError = state.outletName.isEmpty(),
                        isOwnerNameError = state.ownerName.isEmpty(),
                        isBirthDateError = state.birthdate.isEmpty(),
                        isPhone1Error = state.phone1.isEmpty() || !isPhoneNumberValid(state.phone1),
                        isPhone2Error = state.phone2.isNotEmpty() && !isPhoneNumberValid(state.phone2),
                        isTradeLicenseError = state.tradeLicense.isEmpty(),
                        isVatTrnError = state.vatTRN.isEmpty(),
                        isImageError = state.image.isEmpty(),
                        isLoading = false,
                        isEmailError = state.email.isEmpty() || !isEmailValid(state.email),
                        isExpiryDateError = state.tlcExpiryDate.isEmpty()
                    )

                    if (!state.isOutletNameError && !state.isOwnerNameError
                        && !state.isBirthDateError && !state.isPhone1Error
                        && !state.isPhone2Error && !state.isTradeLicenseError
                        && !state.isVatTrnError && !state.isImageError && !state.isEmailError
                    ) {
                        state = state.copy(isLoading = true)
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
                                longitude = state.longitude,
                                marketID = state.marketNameList.first {
                                    it.text.toString().lowercase(Locale.ROOT).equals(
                                        state.marketName.lowercase(Locale.ROOT),
                                        ignoreCase = true
                                    )
                                }.id!!.toInt(),
                                ethnicity = state.ethnicity,
                                email = state.email,
                                routeName = state.routeName,
                                paymentOptions = state.paymentOption
                            )
                        ).onSuccess {

                            state = state.copy(
                                isLoading = false,
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

            is OutletDetailsEvent.OnEthnicityDropDownClick -> {
                state = state.copy(
                    isEthnicityExpanded = !state.isEthnicityExpanded
                )
            }

            is OutletDetailsEvent.OnEthnicitySelection -> {
                state = state.copy(
                    isEthnicityExpanded = false,
                    ethnicity = event.value
                )
            }

            is OutletDetailsEvent.OnPaymentDropDownClick -> {
                state = state.copy(
                    isPaymentOptionsExpanded = !state.isPaymentOptionsExpanded
                )
            }

            is OutletDetailsEvent.OnPaymentOptionSelection -> {
                state = state.copy(
                    paymentOption = event.value,
                    isPaymentOptionsExpanded = false
                )
            }

            is OutletDetailsEvent.OnRouteNameDropDownClick -> {
                state = state.copy(
                    isRouteNameExpanded = !state.isRouteNameExpanded
                )
            }

            is OutletDetailsEvent.OnRouteNameSelection -> {
                state = state.copy(
                    isRouteNameExpanded = false,
                    routeName = event.value
                )
            }

            is OutletDetailsEvent.OnEmailEnter -> {
                state = state.copy(
                    email = event.value,
                    isEmailError = event.value.isEmpty() || !isEmailValid(event.value)
                )
            }

            is OutletDetailsEvent.OnMarketNameSelection -> {
                state = state.copy(
                    isMarketNameExpanded = false,
                    marketID = state.marketNameList.first {
                        it.text.toString().lowercase(Locale.ROOT)
                            .equals(event.value.lowercase(Locale.ROOT), ignoreCase = true)
                    }.id!!.toInt(),
                    marketName = event.value
                )
            }

            is OutletDetailsEvent.OnMarketNameDropDownClick -> {
                state = state.copy(
                    isMarketNameExpanded = !state.isMarketNameExpanded
                )
            }

        }
    }
}