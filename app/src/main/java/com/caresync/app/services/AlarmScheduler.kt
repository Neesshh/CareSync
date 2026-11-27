package com.caresync.app.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.caresync.app.MainActivity

/**
 * Schedules and cancels exact local medication alarms using Android AlarmManager's
 * setAlarmClock(), which bypasses Doze mode rate-limiting so back-to-back medications fire on time.
 */
class AlarmScheduler(private val context: Context) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /**
     * Checks whether the app is currently allowed to schedule exact alarms.
     * On Android 12+ (API 31+), users can revoke this in system settings.
     * Note for future me: Wire this directly into the Settings screen's "Exact Alarms / Fix in Settings" row.
     */
    fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    /**
     * Schedules an exact local alarm clock entry for the given [item].
     *
     * @return true if scheduled successfully, false if exact-alarm permission is missing.
     */
    fun schedule(item: AlarmItem): Boolean {
        if (!canScheduleExactAlarms()) {
            Log.w(TAG, "Exact alarm permission not granted; cannot schedule ${item.medicationName}.")
            return false
        }

        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_MEDICATION_ALARM
            putExtra(AlarmReceiver.EXTRA_SCHEDULE_ID, item.scheduleId)
            putExtra(AlarmReceiver.EXTRA_MEDICATION_NAME, item.medicationName)
            putExtra(AlarmReceiver.EXTRA_DOSAGE_TEXT, item.dosageText)
        }

        // scheduleId.hashCode() keeps each medication schedule from overwriting other PendingIntents.
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            item.scheduleId.hashCode(),
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Opens CareSync if the user taps the alarm icon on the lock screen or status bar.
        val showAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val showAppPendingIntent = PendingIntent.getActivity(
            context,
            0,
            showAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmClockInfo = AlarmManager.AlarmClockInfo(
            item.triggerTimeMillis,
            showAppPendingIntent
        )

        return try {
            alarmManager.setAlarmClock(alarmClockInfo, alarmPendingIntent)
            Log.d(TAG, "Scheduled alarm clock for ${item.medicationName} at ${item.triggerTimeMillis}")
            true
        } catch (e: SecurityException) {
            Log.e(TAG, "SecurityException scheduling exact alarm for ${item.medicationName}", e)
            false
        }
    }

    /**
     * Cancels an existing scheduled alarm matching [scheduleId], if one exists.
     */
    fun cancel(scheduleId: String) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_MEDICATION_ALARM
        }

        // FLAG_NO_CREATE returns the existing PendingIntent (if any) without allocating a new one.
        val existingPendingIntent = PendingIntent.getBroadcast(
            context,
            scheduleId.hashCode(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        if (existingPendingIntent != null) {
            alarmManager.cancel(existingPendingIntent)
            existingPendingIntent.cancel()
            Log.d(TAG, "Canceled alarm for scheduleId=$scheduleId")
        }
    }

    companion object {
        private const val TAG = "AlarmScheduler"
        private const val ACTION_MEDICATION_ALARM = "com.caresync.app.ACTION_MEDICATION_ALARM"
    }
}