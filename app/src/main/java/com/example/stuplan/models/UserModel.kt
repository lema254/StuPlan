package com.example.stuplan.models


data class User(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val userId: String = "",
    val completedSections: Int = 0,
    val totalSections: Int = 0
)
