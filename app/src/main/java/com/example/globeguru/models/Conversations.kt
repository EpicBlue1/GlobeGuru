package com.example.globeguru.models

import androidx.compose.ui.Modifier

data class Conversations(
    val id: String = "",
    val name: String = "",
    val image:  String = "",
    val countryImage:  String = "",
    val totalMessages: Int = 0,
    val modifierCon: Modifier = Modifier,
)