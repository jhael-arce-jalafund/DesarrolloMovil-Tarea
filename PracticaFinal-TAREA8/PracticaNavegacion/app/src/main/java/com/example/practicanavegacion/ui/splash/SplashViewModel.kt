package com.example.practicanavegacion.ui.splash

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
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigation = MutableLiveData<Event<NavDirections>>()
    val navigation: LiveData<Event<NavDirections>> = _navigation

    init { checkAuthState() }

    private fun checkAuthState() {
        viewModelScope.launch {
            val destination = if (authRepository.isUserLoggedIn()) {
                SplashFragmentDirections.actionSplashToDashboard()
            } else {
                SplashFragmentDirections.actionSplashToLogin()
            }
            _navigation.postValue(Event(destination))
        }
    }
}