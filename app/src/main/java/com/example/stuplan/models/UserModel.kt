package com.example.stuplan.models


//data class User(
//    val firstname: String = "",
//    val email: String = "",
//    val password: String = "",
//    val userId: String = "",
//    val profileImageUrl: String
//)


data class UserModel(
    var userId: String = "",
    var name: String? = "",
    var email: String? = null,
    var bio: String? = null,
    var phoneNumber: String? = null,
    var university: String? = null,
    var profileImage: String? = null
) {
    // No-arg constructor required for Firestore
    constructor() : this("", null, null, null, null, null, null)
}