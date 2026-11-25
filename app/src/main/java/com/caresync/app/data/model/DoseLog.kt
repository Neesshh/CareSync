package com.caresync.app.data.model

import com.google.firebase.Timestamp


@Suppress("unused")
data class DoseLog(
    val patientId: String = "",
    val medicationId: String = "",
    val scheduleId: String = "",
    val scheduledAt: Timestamp,
    val status: String = "",
    val reportedAt: Timestamp,
    val undoneAt: Timestamp,
    val doseAmount: Int = 0,
    val doseUnit: String = "",
    val recordedOffline: Boolean,
    val missedAlertSent: Boolean
    )
