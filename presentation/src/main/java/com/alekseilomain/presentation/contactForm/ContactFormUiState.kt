package com.alekseilomain.presentation.contactForm

data class ContactFormUiState(
    val lastName: String = "",
    val email: String = "",
    val isLastNameValid: Boolean = true,
    val isEmailValid: Boolean = true
) {
    val isFormValid: Boolean get() = isLastNameValid && isEmailValid
}
