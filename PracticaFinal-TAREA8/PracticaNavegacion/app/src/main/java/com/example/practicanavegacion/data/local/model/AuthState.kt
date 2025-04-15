package com.example.practicanavegacion.data.local.model

@Entity(tableName = "auth_state")
data class AuthState(
    @PrimaryKey val userId: String,
    val isLoggedIn: Boolean,
    val authToken: String?,
    val lastLogin: Long
)