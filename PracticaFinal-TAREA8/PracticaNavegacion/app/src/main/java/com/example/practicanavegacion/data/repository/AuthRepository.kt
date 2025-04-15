package com.example.practicanavegacion.data.repository

import com.example.practicanavegacion.data.local.AuthDao
import com.example.practicanavegacion.data.local.model.AuthState
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDao: AuthDao
) {
    suspend fun isUserLoggedIn(): Boolean = authDao.getAuthState()?.isLoggedIn ?: false

    suspend fun login(userId: String, token: String) {
        authDao.saveAuthState(
            AuthState(
                userId = userId,
                isLoggedIn = true,
                authToken = token,
                lastLogin = System.currentTimeMillis()
            )
        )
    }

    suspend fun logout() = authDao.clearAuthState()
}