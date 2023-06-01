package com.example.globeguru.models

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val profileImage: String = "",
    val traveller: Boolean = false
)
