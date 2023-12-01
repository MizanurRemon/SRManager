package com.srmanager.outlet_presentation.outlet_add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.auth_presentation.isPhoneNumberValid
import com.srmanager.core.common.util.fileImageUriToBase64
import com.srmanager.database.dao.LocationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OutletAddViewModel @Inject constructor(private val locationDao: LocationDao) : ViewModel() {
    var state by mutableStateOf(OutletAddState())
        private set

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
                        isImageError = false
                    )

                    val requestData = HashMap<String, Any>()
                    requestData["outlet_name"] = state.outletName
                    requestData["owner_name"] = state.ownerName
                    requestData["birth_date"] = state.birthdate
                    requestData["mobile_1"] = state.phone1
                    requestData["mobile_2"] = state.phone2
                    requestData["trade_license"] = state.tradeLicense
                    requestData["vat_trn"] = state.vatTRN
                    requestData["expiry_date"] = state.tlcExpiryDate
                    requestData["address"] = state.address
                    requestData["latitude"] = state.latitude
                    requestData["longitude"] = state.longitude
                    //requestData["photo"] = state.image


                    Log.d("dataxx", "onEvent: $requestData")
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
                state = state.copy(phone2 = event.value)
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