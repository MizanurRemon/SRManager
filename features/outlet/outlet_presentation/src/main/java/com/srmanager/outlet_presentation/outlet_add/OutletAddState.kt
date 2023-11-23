package com.srmanager.outlet_presentation.outlet_add

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class OutletAddState(
    val isShowEntryDialog: MutableState<Boolean> = mutableStateOf(true),
    val outletName: String = "",
    val ownerName: String = "",
    val birthdate: String = "",
    val phone1: String = "",
    val phone2: String = "",
    val tradeLicense: String = "",
    val tlcExpiryDate: String = "",
    val vatTRN: String = "",
    val address: String = ""
)
