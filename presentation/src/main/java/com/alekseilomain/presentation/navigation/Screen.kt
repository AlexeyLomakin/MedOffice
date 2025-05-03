package com.alekseilomain.presentation.navigation

sealed class Screen(val route: String) {
    object Login       : Screen("login")
    object Contacts    : Screen("contacts")
    object ContactForm : Screen("contactForm?mode={mode}&contactId={contactId}") {
        fun createRoute(mode: FormMode, contactId: Long = -1L) =
            "contactForm?mode=${mode.name}&contactId=$contactId"
    }
}

enum class FormMode { ADD, EDIT }
