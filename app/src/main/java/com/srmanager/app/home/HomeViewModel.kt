package com.srmanager.app.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.database.dao.LocationDao
import com.srmanager.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
    private val locationDao: LocationDao
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(HomeState())
        private set

    init {
        state = state.copy(
            isShowDialog = true,
        )
        viewModelScope.launch {
            val isLoggedIn = preferenceDataStoreHelper.getFirstPreference(
                PreferenceDataStoreConstants.IS_LOGGED_IN, false
            )
            state = state.copy(isLoggedIn = isLoggedIn)

            launch {
                launch {
                    locationDao.getLocation().collect {
                        if (it.isNotEmpty()) {
                            state = state.copy(
                                address = it[0].address.toString()
                            )
                        }
                    }
                }

            }

            if (isLoggedIn) {

            }
        }
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLogOut -> {

                viewModelScope.launch {
                    state = state.copy(isLogOutLoading = true)
                    delay(2000)
                    state = state.copy(isLogOutLoading = false)
                    preferenceDataStoreHelper.clearAllPreference()
                    _uiEvent.send(
                        UiEvent.Success
                    )
                }


            }

            else -> {}
        }
    }


}
