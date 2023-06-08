package com.example.globeguru.models

import com.google.firebase.Timestamp

data class Message (
    val UserId: String = "",
    val UserProfile: String = "",
    val message: String = "",
    val time: Timestamp = Timestamp.now()
)

