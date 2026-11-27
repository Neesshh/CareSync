package com.caresync.app.data.model

import java.sql.Timestamp
import java.text.DateFormat


@Suppress("unused")
data class Medication(
    val medicationId: String = "",
    val patientId: String = "",
    val name: String = "",
    val strength: String = "", // Example: "10 mg"
    val instructions: String = "", // Example: "Take one <qualityUnit> by Mouth"
    val quantityRemaining: Double = 0.0, // Example: 30
    val quantityUnit: String = "", // Example: "tablet"
    val prescriber: String = "",
    val discardAfter: Timestamp? = null,
    val pillDescription: String = "",
    val status: String = "active",
    val discontinuedAt: Timestamp? = null
)