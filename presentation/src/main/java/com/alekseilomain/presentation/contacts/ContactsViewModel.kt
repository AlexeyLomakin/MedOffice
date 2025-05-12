package com.alekseilomain.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.usecase.ClearContactsUseCase
import com.alekseilomain.domain.usecase.FetchContactsPageUseCase
import com.alekseilomain.domain.usecase.GetCityUseCase
import com.alekseilomain.domain.usecase.GetLocationUseCase
import com.alekseilomain.domain.usecase.GetSeedUseCase
import com.alekseilomain.domain.usecase.IsLoggedInUseCase
import com.alekseilomain.domain.usecase.LogoutUseCase
import com.alekseilomain.domain.usecase.ObserveContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val observeContactsUseCase: ObserveContactsUseCase,
    private val fetchContactsPageUseCase: FetchContactsPageUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getSeedUseCase: GetSeedUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getCityUseCase: GetCityUseCase,
    private val clearContactsUseCase: ClearContactsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()
    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    private var currentPage = 1

    init {
        viewModelScope.launch {
            observeContactsUseCase()
                .collect { list ->
                    _contacts.value = list }
        }
        viewModelScope.launch {
            val seed = getSeedUseCase().firstOrNull().orEmpty()
            if (isLoggedInUseCase().firstOrNull() == true) {
                getLocationUseCase()?.let { coords ->
                    _city.value = getCityUseCase(coords.latitude, coords.longitude).orEmpty()
                }
                fetchContactsPageUseCase(seed, currentPage)
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            val seed = getSeedUseCase().firstOrNull().orEmpty()
            fetchContactsPageUseCase(seed, ++currentPage)
        }
    }

    fun logoutAndClear() {
        viewModelScope.launch {
            clearContactsUseCase()
            logoutUseCase()
        }
    }
}