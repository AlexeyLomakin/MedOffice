package com.alekseilomain.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    observeContactsUseCase: ObserveContactsUseCase,
    private val fetchContactsPageUseCase: FetchContactsPageUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getSeedUseCase: GetSeedUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getCityUseCase: GetCityUseCase,
    private val clearContactsUseCase: ClearContactsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    private var currentPage = 1

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingFlow: Flow<PagingData<Contact>> =
        observeContactsUseCase()
            .flatMapLatest { contacts ->
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = { ListPagingSource(contacts) }
                ).flow
            }
            .cachedIn(viewModelScope)

    init {

        viewModelScope.launch {
            val seed = getSeedUseCase().firstOrNull().orEmpty()
            if (isLoggedInUseCase().firstOrNull() == true) {
                fetchContactsPageUseCase(seed, currentPage)

                getLocationUseCase()?.let { coords ->
                    val cityName = getCityUseCase(coords.latitude, coords.longitude)
                        .orEmpty()
                    _city.value = cityName
                }
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