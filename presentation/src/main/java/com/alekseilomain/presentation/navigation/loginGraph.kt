package com.alekseilomain.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.alekseilomain.presentation.lang.LanguageViewModel
import com.alekseilomain.presentation.login.LoginScreen
import com.alekseilomain.presentation.login.LoginViewModel

fun NavGraphBuilder.loginGraph(
    navController: NavHostController,
    languageViewModel: LanguageViewModel
) {
    composable(Screen.Login.route) {
        val loginViewModel: LoginViewModel = hiltViewModel()

        val seed by loginViewModel.seed.collectAsState(initial = "")
        val currentLang by languageViewModel.langFlow.collectAsState(initial = "ru")

        LoginScreen(
            seed = seed,
            onSeedChange = loginViewModel::onSeedChange,
            onLoginClick = {
                loginViewModel.login(seed)
                navController.navigate(Screen.Contacts.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            },
            onToggleLanguage = { languageViewModel.toggleLanguage() },
            currentLang = currentLang
        )
    }
}
