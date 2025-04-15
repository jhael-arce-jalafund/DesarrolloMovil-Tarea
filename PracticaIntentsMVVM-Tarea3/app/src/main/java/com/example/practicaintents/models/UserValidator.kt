package com.example.practicaintents.models

class UserValidator {
    fun isValidUser(username: String, password: String): Boolean {
        return (username == "admin" && password == "admin") ||
                (username == "user" && password == "1234")
    }
}
