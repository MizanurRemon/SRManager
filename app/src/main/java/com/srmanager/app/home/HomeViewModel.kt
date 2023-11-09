package com.srmanager.app.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.core.common.util.DEFAULT_LANGUAGE_TAG
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.datastore.PreferenceDataStoreConstants
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import com.srmanager.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,

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

            launch { }

            if (isLoggedIn) {

            }
        }
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLogOut -> {

                state = state.copy(isLogOutLoading = true)

                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Success
                    )
                }
            }

            else -> {}
        }
    }


}
