package com.alekseilomain.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alekseilomain.presentation.contactForm.ContactFormScreen
import com.alekseilomain.presentation.contactForm.ContactFormViewModel


fun NavGraphBuilder.contactFormGraph(
    navController: NavHostController,
) {
    composable(
        route = Screen.ContactForm.route,
        arguments = Screen.ContactForm.navArgs
    ) { back ->
        val mode = FormMode.valueOf(
            back.arguments?.getString(Screen.ContactForm.ARG_MODE) ?: FormMode.ADD.name
        )
        val vm: ContactFormViewModel = hiltViewModel()
        val uiState by vm.uiState.collectAsState()

        ContactFormScreen(
            mode = mode,
            uiState = uiState,
            onLastNameChange = vm::onLastNameChange,
            onEmailChange = vm::onEmailChange,
            onSave = { vm.saveAndExit { navController.popBackStack() } },
            onCancel = { navController.popBackStack() }
        )
    }
}