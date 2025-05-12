package com.alekseilomain.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Contacts : Screen("contacts")
    object ContactForm : Screen("contactForm?mode={mode}&contactId={contactId}") {
        const val ARG_MODE = "mode"
        const val ARG_ID   = "contactId"

        fun createRoute(mode: FormMode, contactId: Long = -1L) =
            "contactForm?mode=${mode.name}&contactId=$contactId"

        val navArgs = listOf(
            navArgument(ARG_MODE) { type = NavType.StringType },
            navArgument(ARG_ID) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    }
}

enum class FormMode { ADD, EDIT }
