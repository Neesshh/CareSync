package com.caresync.app.data.model

import com.google.firebase.Timestamp

/*
    CareSync user profile
    @Suppress("unused") since it isn't being used yet
*/

@Suppress("unused")
data class User(
    val userId: String = "",
    val role: String = "",
    val name: Name = Name(), // creates an empty first, middle, and last name
    val email: String = "",
    val phone: String? = null,
    val timeZone: String = "",
    val createdAt: Timestamp = Timestamp.now() // unsure what datatype to default this to
)