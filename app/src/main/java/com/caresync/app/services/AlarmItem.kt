package com.caresync.app.services

/**
 * Lightweight local model representing a single scheduled medication reminder.
 * Kept decoupled from Firestore models so local alarms can be scheduled and tested
 * independently; a simple mapper will convert Nelson's medication + schedule models
 * into AlarmItem in Sprint 3.
 *
 * @property scheduleId Unique identifier for this schedule (used to generate a unique PendingIntent request code).
 * @property medicationName Display name of the medication (example "Aspirin").
 * @property dosageText Dose and instruction text shown in the alert (example "325 mg · Take one tablet by mouth").
 * @property triggerTimeMillis Epoch timestamp in milliseconds when the alarm should fire.
 */


data class AlarmItem (
    val scheduleId: String,
    val medicationName: String,
    val dosageText: String,
    val triggerTimeMillis: Long
)
