package com.alekseilomain.presentation.lang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val repo: LanguageRepository
) : ViewModel() {

    val langFlow: StateFlow<String> = repo.getLangFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "ru")

    private val _languageChanged = MutableSharedFlow<String>(
        replay = 0, extraBufferCapacity = 1
    )
    val languageChanged = _languageChanged.asSharedFlow()

    fun toggleLanguage() {
        viewModelScope.launch {
            val current = langFlow.value
            val next = if (current == "ru") "en" else "ru"
            repo.saveLang(next)
            _languageChanged.emit(next)
        }
    }
}
