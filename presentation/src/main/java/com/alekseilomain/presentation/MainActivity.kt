package com.alekseilomain.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.core.DataStore
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import com.alekseilomain.presentation.navigation.MedOfficeNavGraph
import com.alekseilomain.presentation.navigation.Screen
import com.alekseilomain.domain.usecase.IsLoggedInUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val languageViewModel: LanguageViewModel by viewModels()

    private val isLoggedInUseCase: IsLoggedInUseCase by lazy {
        EntryPointAccessors.fromApplication(
            applicationContext,
            MainEntryPoint::class.java
        ).isLoggedInUseCase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val langTag = languageViewModel.langFlow.first()
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(langTag)
            )
            val loggedIn = isLoggedInUseCase().first()
            setContent {
                MedOfficeNavGraph(
                    startDestination = if (loggedIn) Screen.Contacts.route else Screen.Login.route
                )
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface MainEntryPoint {
        fun isLoggedInUseCase(): IsLoggedInUseCase
    }
}