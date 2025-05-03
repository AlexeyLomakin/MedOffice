package com.alekseilomain.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.prefs.Preferences
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val prefs: DataStore<Preferences>
) : ViewModel() {

    companion object {
        private val KEY_LANG = stringPreferencesKey("lang")
    }

    // сразу читаем из DataStore
    val langFlow = prefs.data
        .map { it[KEY_LANG] ?: "ru" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "ru")

    fun toggleLanguage() {
        viewModelScope.launch {
            val new = if (langFlow.value == "ru") "en" else "ru"
            prefs.edit { it[KEY_LANG] = new }
        }
    }
}