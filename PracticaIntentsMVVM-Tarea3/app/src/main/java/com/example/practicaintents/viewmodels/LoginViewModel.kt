package com.example.practicaintents.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicaintents.models.UserValidator

class LoginViewModel : ViewModel() {
    private val validator = UserValidator()

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun login(username: String, password: String) {
        val success = validator.isValidUser(username, password)
        _loginSuccess.value = success
        if (success) _username.value = username
    }
}
