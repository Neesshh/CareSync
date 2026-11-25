package com.caresync.app.data.model

import java.sql.Timestamp
import java.text.DateFormat


@Suppress("unused")
data class Medication(
    val medicationId: String = "",
    val patientId: String = "",
    val name: String = "",
    val strength: String = "", // is this a combination of quantityRemaining and quantityUnit?
    val quantityRemaining: Double = 0.0,
    val quantityUnit: Int = 0,
    val prescriber: String = "",
    val discardAfter: Timestamp,
    val pillDescription: String = "",
    val status: String = "",
    val discontinueAt: Timestamp
)