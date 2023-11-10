package com.srmanager.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.app.location.LocationLiveData
import com.srmanager.core.common.util.DEFAULT_LANGUAGE_TAG
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
    private val application: Application
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val locationLiveData = LocationLiveData(application)
    fun onBack() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    init {
        viewModelScope.launch {
            val isLoggedIn = preferenceDataStoreHelper.getFirstPreference(
                PreferenceDataStoreConstants.IS_LOGGED_IN,
                false
            )
            if (isLoggedIn) _uiEvent.send(UiEvent.Success) else _uiEvent.send(UiEvent.NavigateUp)

        }

        startLocationUpdates()


    }


    fun getLocationLiveData() = locationLiveData


    private fun startLocationUpdates(){
        locationLiveData.startLocationUpdates()
       // Log.d("locationxx", "locationLiveData ${locationLiveData.value!!.latitude} ")
    }

}