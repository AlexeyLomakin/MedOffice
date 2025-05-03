package com.alekseilomain.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alekseilomain.presentation.LanguageViewModel
import com.alekseilomain.presentation.Login.LoginScreen
import com.alekseilomain.presentation.Login.LoginViewModel
import com.alekseilomain.presentation.contacts.ContactsScreen
import com.alekseilomain.presentation.contacts.ContactsViewModel

@Composable
fun MedOfficeNavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    val langVm: LanguageViewModel = hiltViewModel()
    val currentLang by langVm.langFlow.collectAsState(initial = "ru")

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            val vm: LoginViewModel = hiltViewModel<LoginViewModel>()
            val seed by vm.seed.collectAsState(initial = "")
            LoginScreen(
                seed = seed,
                onSeedChange = vm::onSeedChange,
                onLoginClick = {
                    vm.login(it)
                    navController.navigate(Screen.Contacts.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onToggleLanguage = langVm::toggleLanguage,
                currentLang = currentLang
            )
        }

        composable(Screen.Contacts.route) {
            val vm: ContactsViewModel = hiltViewModel()
            val contacts by vm.contacts.collectAsState()
            val city     by vm.city.collectAsState()
            ContactsScreen(
                contacts = contacts,
                city = city,
                onAddClick = {
                    navController.navigate(
                        Screen.ContactForm.createRoute(FormMode.ADD)
                    )
                },
                onEditClick = { id ->
                    navController.navigate(
                        Screen.ContactForm.createRoute(FormMode.EDIT, id)
                    )
                },
                onLogoutClick = {
                    vm.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Contacts.route) { inclusive = true }
                    }
                },
                onToggleLanguage = langVm::toggleLanguage,
                currentLang = currentLang
            )
        }

        composable(
            route = Screen.ContactForm.route,
            arguments = listOf(
                navArgument("mode") { type = NavType.StringType },
                navArgument("contactId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { back ->
            val mode = FormMode.valueOf(back.arguments!!.getString("mode")!!)
            val id   = back.arguments!!.getLong("contactId")
            val vm: ContactFormViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsState()
            ContactFormScreen(
                mode = mode,
                uiState = uiState,
                onSave = { last, email ->
                    vm.save(id, last, email)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                },
                onToggleLanguage = langVm::toggleLanguage,
                currentLang = currentLang
            )
        }
    }
}






