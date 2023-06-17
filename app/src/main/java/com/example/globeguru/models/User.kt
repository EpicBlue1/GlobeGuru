package com.example.globeguru.models

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val profileImage: String = "",
    val city: String = "",
    val cityCode: String = "",
    val traveller: Boolean = false
)
