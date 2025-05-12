package com.alekseilomain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alekseilomain.presentation.lang.LanguageViewModel

@Composable
fun MedOfficeNavGraph(
    startDestination: String,
    languageViewModel: LanguageViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination) {
        loginGraph(navController, languageViewModel)
        contactsGraph(navController, languageViewModel)
        contactFormGraph(navController)
    }
}