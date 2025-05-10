package com.example.stuplan.models

data class User(
    val uid: String = "",
    val username: String = "",
    val displayName: String = "",
    val bio: String = "",
    val profileImageUrl: String? = "",
    val email: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val posts: Int = 0,
    val website: String = "",
    val phoneNumber: String = "",
    val phone: String = "",
    val name: String,
    var createdAt: Long = 0L

    )