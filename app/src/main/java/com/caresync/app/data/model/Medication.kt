package com.caresync.app.data.model

import java.sql.Timestamp
import java.text.DateFormat


@Suppress("unused")
data class Medication(
    val medicationId: String = "",
    val patientId: String = "",
    val name: String = "",
    val doseAmount: Double = 0.0,
    val doseUnit: String = "",
    val type: String = "",
    val instructions: String = "",
    val quantityRemaining: Int = 0,
    val quantityUnit: String = "",
    val status: String = "active",
    val createdAtMillis : Long = 0L
)