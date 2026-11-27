package com.caresync.app.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * BroadcastReceiver triggered by Android AlarmManager when a scheduled medication alarm fires.
 * Runs offline without requiring Firebase or an active network connection.***
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val scheduleId = intent.getStringExtra(EXTRA_SCHEDULE_ID) ?: DEFAULT_SCHEDULE_ID
        val medicationName = intent.getStringExtra(EXTRA_MEDICATION_NAME) ?: "Medication"
        val dosageText = intent.getStringExtra(EXTRA_DOSAGE_TEXT) ?: "Time to take your scheduled dose."

        Log.d(TAG, "Alarm received for scheduleId=$scheduleId, medication=$medicationName")

        AlarmNotificationHelper.showReminderNotification(
            context = context,
            scheduleId = scheduleId,
            medicationName = medicationName,
            dosageText = dosageText
        )
    }

    companion object {
        private const val TAG = "AlarmReceiver"
        private const val DEFAULT_SCHEDULE_ID = "caresync_default_alarm"

        const val EXTRA_SCHEDULE_ID = "com.caresync.app.EXTRA_SCHEDULE_ID"
        const val EXTRA_MEDICATION_NAME = "com.caresync.app.EXTRA_MEDICATION_NAME"
        const val EXTRA_DOSAGE_TEXT = "com.caresync.app.EXTRA_DOSAGE_TEXT"
    }
}