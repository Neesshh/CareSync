package com.caresync.app.data.model


@Suppress("unused")
data class DoseLog(
    val occurrenceId: String = "",
    val scheduleId: String = "",
    val medicationId: String = "",
    val patientId: String = "",
    val doseAmount: Double = 0.0,
    val doseUnit: String = "",
    val status: String = "UPCOMING",
    val scheduledTimeMillis: Long = 0L,
    val reportedTimeMillis: Long? = null
)
