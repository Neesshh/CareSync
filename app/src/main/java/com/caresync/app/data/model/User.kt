package com.caresync.app.data.model

/*
    CareSync user profile
*/

data class User(
    val userId: String = "",
    val name: Map<String, String> = mapOf(
        "first" to "",
        "middle" to "",
        "last" to ""
    ),
    val email: String = "",
    val role: String = "",
    val phone: String? = null,
    val timeZone: String = ""
)
