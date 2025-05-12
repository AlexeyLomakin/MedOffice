package com.alekseilomain.presentation.lang

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val prefs: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) {
    private val KEY_LANG = stringPreferencesKey("lang")
    private val SHARED_PREFS_NAME = "language_prefs"

    fun getLangFlow(): Flow<String> =
        prefs.data.map { it[KEY_LANG] ?: "ru" }

    suspend fun saveLang(lang: String) {
        prefs.edit { it[KEY_LANG] = lang }
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString("lang", lang).apply()
    }
}
