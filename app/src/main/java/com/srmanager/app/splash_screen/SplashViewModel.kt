package com.srmanager.app.splash_screen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
    private val locationDao: LocationDao
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var state by mutableStateOf(SplashScreenState())
        private set


    init {
        state = state.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)

            locationDao.getLocation().collect {
                state = state.copy(isLoading = false,address = mutableStateOf(it[0].address.toString()))
            }

            delay(2000)

            val isLoggedIn = preferenceDataStoreHelper.getFirstPreference(
                PreferenceDataStoreConstants.IS_LOGGED_IN,
                false
            )
            if (isLoggedIn) _uiEvent.send(UiEvent.Success) else _uiEvent.send(UiEvent.NavigateUp)

        }

    }

}