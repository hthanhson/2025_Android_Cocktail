package com.example.cocktaildb.login.presentation.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val password: String? = null
)
