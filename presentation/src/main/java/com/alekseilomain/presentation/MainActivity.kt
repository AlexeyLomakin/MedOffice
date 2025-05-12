package com.alekseilomain.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.alekseilomain.domain.usecase.IsLoggedInUseCase
import com.alekseilomain.presentation.lang.LanguageViewModel
import com.alekseilomain.presentation.navigation.MedOfficeNavGraph
import com.alekseilomain.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var isLoggedInUseCase: IsLoggedInUseCase

    private val languageViewModel: LanguageViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val sharedPrefs = newBase.getSharedPreferences("language_prefs", MODE_PRIVATE)
        val lang = sharedPrefs.getString("lang", "ru") ?: "ru"
        val locale = Locale(lang)

        val config = Configuration(newBase.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        val localizedContext = newBase.createConfigurationContext(config)

        super.attachBaseContext(localizedContext)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MAIN_ACTIVITY", "onCreate: activity recreated")

        val fineOk = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseOk = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineOk || coarseOk) {
            loadApp()
        } else {
            requestPermissions()
        }

        lifecycleScope.launch {
            Log.d("MAIN_ACTIVITY", "Listening for languageChanged")
            languageViewModel.languageChanged.collect { newLang ->
                val current = AppCompatDelegate.getApplicationLocales().toLanguageTags()
                if (newLang != current) {
                    Log.d("MAIN_ACTIVITY", "Language changed → recreate()")
                    recreate()
                }
            }
        }
    }

    private fun requestPermissions() {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            val granted = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
                    || perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (granted) loadApp() else finish()
        }.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun loadApp() {
        lifecycleScope.launch {
            val loggedIn = isLoggedInUseCase().firstOrNull() == true
            setContent {
                MedOfficeNavGraph(
                    startDestination = if (loggedIn) Screen.Contacts.route
                    else Screen.Login.route,
                    languageViewModel = languageViewModel
                )
            }
        }
    }
}
