package com.example.projetmobile

data class Question(
    val question: String,
    val options: List<String>,
    val correctOption: Int
)