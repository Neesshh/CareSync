package com.caresync.app.data.model

import com.google.firebase.Timestamp

@Suppress("unused")
data class Schedule(
    val scheduleId: String = "",
    val medicationId: String = "",
    val patientId: String = "",
    val doseAmount: Double = 0.0,
    val doseUnit: String = "",
    val times: List<String> = emptyList(),
    val daysOfWeek: List<String> = emptyList(),
    val effectiveFrom: Timestamp? = null,
    val effectiveUntil: Timestamp? = null
)