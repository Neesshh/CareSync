package com.caresync.app.data.model

/*
    CareSync user profile
    @Suppress("unused") since it isn't being used yet
*/

data class Name(
    val first: String = "",
    val middle: String = "",
    val last: String = ""
)


@Suppress("unused")
data class User(
    val userId: String = "",
    val name: Name,
    val email: String = "",
    val role: String = "",
    val phone: String? = null,
    val timeZone: String = ""
)