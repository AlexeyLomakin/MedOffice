package com.alekseilomain.presentation.contactForm

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import com.alekseilomain.presentation.navigation.FormMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactFormViewModel @Inject constructor(
    private val repo: ContactsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mode = FormMode.valueOf(savedStateHandle["mode"] ?: FormMode.ADD.name)
    private val contactId = savedStateHandle["contactId"] ?: -1L
    private val NAME_REGEX = Regex("^[A-Za-zА-Яа-яЁё\\-]{1,25}$")
    private val _uiState = MutableStateFlow(ContactFormUiState())
    val uiState: StateFlow<ContactFormUiState> = _uiState

    init {
        if (mode == FormMode.EDIT && contactId != -1L) {
            viewModelScope.launch {
                repo.observeAll()
                    .firstOrNull()
                    ?.firstOrNull { it.id == contactId }
                    ?.let { c ->
                        _uiState.update {
                            it.copy(
                                lastName = c.lastName,
                                email    = c.email
                            )
                        }
                    }
            }
        }
    }

    fun onLastNameChange(value: String) {
        _uiState.update {
            it.copy(
                lastName        = value,
                isLastNameValid = NAME_REGEX.matches(value)
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(
                email        = value,
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(value).matches()
            )
        }
    }

    fun saveAndExit(onDone: () -> Unit) {
        val s = _uiState.value
        if (!s.isFormValid) return
        viewModelScope.launch {
            repo.upsert(
                Contact(
                    id       = if (mode == FormMode.ADD) 0L else contactId,
                    lastName = s.lastName,
                    email    = s.email,
                    isManual = true
                )
            )
            onDone()
        }
    }
}
