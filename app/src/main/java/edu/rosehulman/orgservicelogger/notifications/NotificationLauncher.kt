package edu.rosehulman.orgservicelogger.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import com.google.firebase.Timestamp
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.retrieveFutureNotifications

object NotificationLauncher {
    fun descheduleNotifications(context: Context, personId: String) {
        retrieveFutureNotifications(personId) { notifications ->
            for (notification in notifications) {
                val pendingIntent = makePendingIntent(context, notification.id!!)
                getAlarmManager(context).cancel(pendingIntent)
            }
        }
    }

    fun scheduleNotifications(context: Context, personId: String) {
        retrieveFutureNotifications(personId) { notifications ->
            for (notification in notifications) {
                scheduleNotification(context, notification.id!!, notification.time)
            }
        }
    }

    fun scheduleNotification(context: Context, notificationId: String, time: Timestamp) {
        val pendingIntent = makePendingIntent(context, notificationId)
        getAlarmManager(context).set(AlarmManager.RTC_WAKEUP, time.toDate().time, pendingIntent)
    }

    private fun getAlarmManager(context: Context) =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun makePendingIntent(context: Context, notificationId: String) =
        PendingIntent.getService(
            context, notificationId.hashCode(),
            CreateNotificationService.createIntent(context, notificationId), 0
        )

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationService =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val reminderChannel =
                NotificationChannel(
                    Notification.TYPE_REMINDER,
                    "Reminder",
                    NotificationManager.IMPORTANCE_HIGH
                )
            reminderChannel.description = "Reminder to attend event"
            notificationService.createNotificationChannel(reminderChannel)

            val confirmChannel =
                NotificationChannel(
                    Notification.TYPE_CONFIRM,
                    "Confirm",
                    NotificationManager.IMPORTANCE_HIGH
                )
            confirmChannel.description = "Reminder to confirm event attendance"
            notificationService.createNotificationChannel(confirmChannel)

            val needsReplacementChannel =
                NotificationChannel(
                    Notification.TYPE_NEEDS_REPLACEMENT,
                    "Needs Replacement",
                    NotificationManager.IMPORTANCE_HIGH
                )
            needsReplacementChannel.description =
                "A member of your organization needs a replacement"
            notificationService.createNotificationChannel(needsReplacementChannel)
        }
    }
}
