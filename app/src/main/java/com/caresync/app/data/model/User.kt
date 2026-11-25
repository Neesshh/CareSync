package com.caresync.app.data.model

/*
    CareSync user profile
    @Suppress("unused") since it isn't being used yet
*/

@Suppress("unused")
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