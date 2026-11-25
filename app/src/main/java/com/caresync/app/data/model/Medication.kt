package com.caresync.app.data.model

import java.sql.Timestamp
import java.text.DateFormat


@Suppress("unused")
data class Medication(
    val medicationId: String = "",
    val patientId: String = "",
    val name: String = "",
    val quantityRemaining: Int = 0,
    val quantityUnit: String = "",
    val prescriber: String? = null,
    val discardAfter: DateFormat,
    val pillDescription: String? = null,
    val status: String = "active",
    val discontinueAt: Timestamp? = null
)