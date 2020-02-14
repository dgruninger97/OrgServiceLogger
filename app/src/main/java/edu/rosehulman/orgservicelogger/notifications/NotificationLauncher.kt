package edu.rosehulman.orgservicelogger.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.Timestamp

object NotificationLauncher {
    fun launchNotification(context: Context, notificationId: String, time: Timestamp) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = CreateNotificationService.createIntent(context, notificationId)
        val pendingIntent = PendingIntent.getService(context, notificationId.hashCode(), intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, time.toDate().time, pendingIntent)
    }
}
