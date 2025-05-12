package com.alekseilomain.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alekseilomain.presentation.lang.LanguageViewModel
import com.alekseilomain.presentation.contacts.ContactsScreen
import com.alekseilomain.presentation.contacts.ContactsViewModel

fun NavGraphBuilder.contactsGraph(
    navController: NavHostController,
    languageViewModel: LanguageViewModel
) {
    composable(Screen.Contacts.route) {
        val vm: ContactsViewModel = hiltViewModel()

        val contacts by vm.contacts.collectAsState()
        val city by vm.city.collectAsState(initial = "")
        val currentLang by languageViewModel.langFlow.collectAsState(initial = "ru")

        key(currentLang) {
            ContactsScreen(
                contacts = contacts,
                city = city,
                currentLang = currentLang,
                onLogout = {
                    vm.logoutAndClear()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Contacts.route) { inclusive = true }
                    }
                },
                onAddContact = {
                    navController.navigate(Screen.ContactForm.createRoute(FormMode.ADD))
                },
                onEditContact = { id ->
                    navController.navigate(Screen.ContactForm.createRoute(FormMode.EDIT, id))
                },
                onToggleLanguage = languageViewModel::toggleLanguage,
                onLoadNextPage = vm::loadNextPage
            )
        }
    }
}