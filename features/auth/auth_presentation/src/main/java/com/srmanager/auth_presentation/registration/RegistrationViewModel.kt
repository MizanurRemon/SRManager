package com.srmanager.auth_presentation.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srmanager.auth.auth_domain.model.RegistrationModel
import com.srmanager.auth.auth_domain.use_cases.AuthUseCases
import com.srmanager.core.common.util.UiEvent
import com.srmanager.core.common.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCases: AuthUseCases,
) : ViewModel() {
    var state by mutableStateOf(RegistrationState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun back() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    fun isEmailValid() {
        if (state.email.isEmpty()) return
        state =
            state.copy(isEmailValid = mutableStateOf(registrationUseCases.emailValidate(state.email)).value)
    }

    fun isPasswordValid() {
        if (state.password.isEmpty()) return
        state =
            state.copy(isPasswordValid = mutableStateOf(registrationUseCases.passwordValidate(state.password)).value)
    }

    fun isConfirmPasswordValid() {
        if (state.confirmPassword.isEmpty()) return
        state =
            state.copy(
                isConfirmPasswordValid = state.isPasswordValid &&
                        state.password == state.confirmPassword
            )
    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnEmailEnter -> {
                state = state.copy(
                    email = event.email,
                    isEmailValid = !mutableStateOf(registrationUseCases.emailValidate(event.email)).value
                )
            }

            is RegistrationEvent.OnPasswordEnter -> {
                state = state.copy(
                    password = event.password,
                    isPasswordValid = !mutableStateOf(registrationUseCases.passwordValidate(event.password)).value
                )
            }

            is RegistrationEvent.OnConfirmationPasswordEnter -> {
                state = state.copy(
                    confirmPassword = event.confirmPassword,
                    isConfirmPasswordValid = !mutableStateOf(
                        registrationUseCases.passwordValidate(
                            event.confirmPassword
                        ) && state.password == event
                            .confirmPassword
                    ).value,
                )

            }


            is RegistrationEvent.OnSubmitClick -> {

                viewModelScope.launch {
                    if (registrationUseCases.emailValidate(state.email) &&
                        registrationUseCases.passwordValidate(state.password) &&
                        state.confirmPassword == state.password
                    ) {

                        Log.d("dataxx", "onEvent: if")
                        state = state.copy(
                            showDialog = true,
                            isEmailValid = mutableStateOf(true).value,
                            isPasswordValid = mutableStateOf(true).value,
                            isConfirmPasswordValid = true,
                        )
                        /*registrationUseCases.postRegistrationData(
                            RegistrationModel(
                                email = state.email,
                                password = state.password,
                                confirmPassword = state.confirmPassword,
                                language = ""
                            )
                        ).onSuccess {
                            state = state.copy(showDialog = false)
                            _uiEvent.send(
                                UiEvent.Success
                            )

                        }.onFailure {
                            state = state.copy(showDialog = false)
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    UiText.DynamicString(
                                        it.message ?: ""
                                    )
                                )
                            )

                        }*/
                    } else {
                        state = state.copy(
                            isEmailValid = !mutableStateOf(registrationUseCases.emailValidate(state.email)).value,
                            isPasswordValid = !mutableStateOf(
                                registrationUseCases.passwordValidate(
                                    state.password
                                )
                            ).value,
                            isConfirmPasswordValid = !mutableStateOf(
                                registrationUseCases.passwordValidate(
                                    state.confirmPassword
                                ) && state.password == state.confirmPassword
                            ).value,
                        )
                    }
                }
            }

            else -> {}
        }
    }
}

