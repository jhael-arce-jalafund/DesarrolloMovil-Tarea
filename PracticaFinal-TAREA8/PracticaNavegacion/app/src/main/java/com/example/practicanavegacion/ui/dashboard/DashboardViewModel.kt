package com.example.practicanavegacion.ui.dashboard
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
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigation = MutableLiveData<Event<NavDirections>>()
    val navigation: LiveData<Event<NavDirections>> = _navigation

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _navigation.postValue(Event(DashboardFragmentDirections.actionDashboardToLogin()))
        }
    }
}