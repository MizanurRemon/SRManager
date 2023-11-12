package com.srmanager.app.splash_screen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.app.location.LocationLiveData
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.database.dao.LocationDao
import com.srmanager.database.entity.LocationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
    application: Application,
    private val locationDao: LocationDao
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val locationLiveData = LocationLiveData(application)

    val address = mutableStateOf("")


    fun onBack() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    init {

        locationLiveData.observeForever {
            viewModelScope.launch(Dispatchers.IO) {
                val isLoggedIn = preferenceDataStoreHelper.getFirstPreference(
                    PreferenceDataStoreConstants.IS_LOGGED_IN,
                    false
                )
                if (isLoggedIn) _uiEvent.send(UiEvent.Success) else _uiEvent.send(UiEvent.NavigateUp)


                locationDao.insertLocation(
                    LocationEntity(
                        latitude = it.latitude.toString(),
                        longitude = it.longitude.toString(),
                        address = it.address.toString()
                    )
                )

            }

        }


    }

}