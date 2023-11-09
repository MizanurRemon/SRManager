package com.srmanager.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.DEFAULT_LANGUAGE_TAG
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


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
    }

}