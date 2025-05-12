package com.alekseilomain.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alekseilomain.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _seed = MutableStateFlow("")
    val seed: StateFlow<String> = _seed.asStateFlow()



    fun onSeedChange(newSeed: String) {
        _seed.value = newSeed
    }

    fun login(seedValue: String) {
        viewModelScope.launch {
            loginUseCase(seedValue)
        }
    }
}