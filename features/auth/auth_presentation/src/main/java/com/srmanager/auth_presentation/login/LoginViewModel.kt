package com.srmanager.auth_presentation.login

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.srmanager.auth.auth_domain.model.AuthenticationModel
import com.srmanager.auth.auth_domain.model.LoginModel
import com.srmanager.auth.auth_domain.use_cases.AuthUseCases
import com.srmanager.core.common.navigation.Route
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import com.srmanager.core.datastore.PreferenceDataStoreConstants
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


    fun isEmailValid() {
        if (state.email.isEmpty()) return
        state = state.copy(isEmailValid = authUseCases.emailValidate(state.email))
    }




    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailEnter -> {
                state = state.copy(
                    email = event.email
                )
            }

            is LoginEvent.OnPasswordEnter -> {
                state = state.copy(password = event.password)
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
            if (authUseCases.emailValidate(state.email) && state.password.isNotEmpty()) {
                state = state.copy(
                    isShowDialog = true,
                    isEmailValid = true,
                    isPasswordValid = true,
                )
                authUseCases.loginUseCase(
                    LoginModel(
                        email = state.email,
                        password = state.password,
                    )
                ).onSuccess {

                    state = state.copy(
                        isShowDialog = false,
                        tag = if (it.userProfile.language == "dutch") "nl" else "en"
                    )

                    preferenceDataStoreHelper.putPreference(
                        PreferenceDataStoreConstants.LANGUAGE_TAG,
                        state.tag
                    )
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            state.tag
                        )
                    )
                    _uiEvent.send(
                        UiEvent.Success
                    )
                }.onFailure {

                    if (it.message == "404") {
                        authUseCases.authenticationDraftUseCase(
                            AuthenticationModel(
                                username = state.email,
                                password = state.password,
                                rememberMe = false,
                                deviceId = ""
                            )
                        ).onSuccess {

                            state = state.copy(isShowDialog = false, isDraftUser = true)
                            _uiEvent.send(
                                UiEvent.Success
                            )
                        }.onFailure {
                            state = state.copy(isShowDialog = false)
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        it.message.toString()
                                    )
                                )
                            )
                        }
                    } else {
                        state = state.copy(isShowDialog = false)
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(
                                    it.message ?: ""
                                )
                            )
                        )
                    }

                }
            } else {
                state = state.copy(
                    isEmailValid = authUseCases.emailValidate(state.email),
                    isPasswordValid = state.password.isNotEmpty(),
                )
            }
        }
    }
}

