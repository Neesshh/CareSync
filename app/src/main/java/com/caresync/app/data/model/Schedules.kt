package com.caresync.app.data.model

import java.text.DateFormat

@Suppress("unused")
data class Schedules(
    val scheduleId: String = "",
    val mediationId: String = "",
    val patientId: String = "",
    val doseAmount: Float,
    val times: List<String>,
    val daysOfWeek: List<String>,
    val effectiveFrom: DateFormat,
    val effectiveUntil: DateFormat
)