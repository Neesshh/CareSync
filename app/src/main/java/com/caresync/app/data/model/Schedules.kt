package com.caresync.app.data.model

import java.text.DateFormat

@Suppress("unused")
data class Schedules(
    val scheduleId: String = "",
    val mediationId: String = "",
    val patientId: String = "",
    val doseAmount: Double,
    val doseUnit: String = "",
    val times: ArrayList<String>, // using ArrayList for mutability
    val daysOfWeek: ArrayList<String>, // using ArrayList for mutability
    val effectiveFrom: DateFormat,
    val effectiveUntil: DateFormat
)