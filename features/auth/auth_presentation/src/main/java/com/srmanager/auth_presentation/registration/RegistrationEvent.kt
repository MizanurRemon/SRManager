package com.srmanager.auth_presentation.registration

sealed class RegistrationEvent {
    data class OnEmailEnter(val email: String): RegistrationEvent()
    data class OnPasswordEnter(val password: String): RegistrationEvent()
    data class OnConfirmationPasswordEnter(val confirmPassword: String): RegistrationEvent()
    object OnSubmitClick: RegistrationEvent()

}
