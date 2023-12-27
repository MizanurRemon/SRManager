package com.srmanager.auth_presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.auth.auth_domain.model.LoginModel
import com.srmanager.auth.auth_domain.use_cases.AuthUseCases
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.datastore.PreferenceDataStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper,
    private val authUseCases: AuthUseCases,
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnUserNameEnter -> {
                state = state.copy(
                    userName = event.userName,
                    isUserNameValid = event.userName.isEmpty()
                )
            }

            is LoginEvent.OnPasswordEnter -> {
                state = state.copy(password = event.password, isPasswordValid = event.password.isEmpty())
            }

            is LoginEvent.OnSubmitClick -> {
                signIn()
            }

            is LoginEvent.OnSignUpClick -> {

            }

        }
    }

    private fun signIn() {
        viewModelScope.launch {
            if (state.userName.isNotEmpty() && state.password.isNotEmpty()) {
                state = state.copy(
                    isShowDialog = true,
                    isUserNameValid = state.userName.isEmpty(),
                    isPasswordValid =  state.password.isEmpty(),
                )
                authUseCases.loginUseCase(
                    LoginModel(
                        email = state.userName,
                        password = state.password,
                    )
                ).onSuccess {

                    _uiEvent.send(
                        UiEvent.Success
                    )
                }.onFailure {
                    state = state.copy(isShowDialog = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                it.message ?: ""
                            )
                        )
                    )
                }
            } else {
                state = state.copy(
                    isUserNameValid = state.userName.isEmpty(),
                    isPasswordValid = state.password.isEmpty(),
                )
            }
        }
    }
}

