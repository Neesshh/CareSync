package com.caresync.app.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.caresync.app.MainActivity

/**
 * Manages the CareSync medication reminder notification channel and builds/displays
 * local reminder notifications when an alarm fires.
 */
object AlarmNotificationHelper{
    private const val TAG = "AlarmNotificationHelper"
    const val CHANNEL_ID = "caresync_medication_reminders"
    private const val CHANNEL_NAME = "Medication Reminders"
    private const val CHANNEL_DESCRIPTION = "High-priority local alerts for scheduled CareSync medications."

    /**
     * Ensures the high-importance notification channel exists on Android 8.0+ (API 26+).
     * safe to call repeatedly. Android ignores creation if the channel already exists.
     */
    fun ensureNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    /**
     * Posts a visible medication reminder notification with sound and vibration.
     * Note for future me: Android 13+ (API 33+) requires runtime POST_NOTIFICATIONS permission;
     * if it is not granted, we log and exit safely instead of crashing.
     */

    fun showReminderNotification(
        context: Context,
        scheduleId: String,
        medicationName: String,
        dosageText: String
    ) {
        ensureNotificationChannel(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                Log.w(TAG, "POST_NOTIFICATIONS not granted; cannot display alarm for $medicationName.")
                return
            }
        }
        // Tapping the notification opens CareSync (sprint 3 will deep link to the reminder alert screen)
        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val tapPendingIntent = PendingIntent.getActivity(
            context,
            scheduleId.hashCode(),
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // using a built in Android alarm icon for spring 2 so this compiles without custome drawable dependency
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Time to Take $medicationName")
            .setContentText(dosageText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(dosageText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(tapPendingIntent)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(scheduleId.hashCode(), notification)
    }
}