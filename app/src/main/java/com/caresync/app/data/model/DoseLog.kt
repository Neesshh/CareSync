package com.caresync.app.data.model

import com.google.firebase.Timestamp


@Suppress("unused")
data class DoseLog(
    val logId: String = "",
    val patientId: String = "",
    val medicationId: String = "",
    val scheduleId: String = "",
    val scheduledAt: Timestamp? = null,
    val status: String = "scheduled",
    val reportedAt: Timestamp? = null,
    val undoneAt: Timestamp? = null,
    val doseAmount: Double = 0.0,
    val doseUnit: String = "",
    val recordedOffline: Boolean = false,
    val missedAlertSent: Boolean = false
    )
