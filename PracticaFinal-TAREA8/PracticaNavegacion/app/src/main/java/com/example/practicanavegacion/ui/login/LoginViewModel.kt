package com.example.practicanavegacion.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.practicanavegacion.data.Event
import com.example.practicanavegacion.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigation = MutableLiveData<Event<NavDirections>>()
    val navigation: LiveData<Event<NavDirections>> = _navigation

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login("user_${System.currentTimeMillis()}", "token_123")
            _navigation.postValue(Event(LoginFragmentDirections.actionLoginToDashboard()))
        }
    }
}