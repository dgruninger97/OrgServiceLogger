package edu.rosehulman.orgservicelogger.notifications

import android.app.IntentService
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Notification

// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_VIEW_EVENT =
    "edu.rosehulman.orgservicelogger.notifications.action.VIEW_EVENT"

class CreateNotificationService : IntentService("ViewEventService") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent?.action == ACTION_VIEW_EVENT) {
            val event = intent.getStringExtra("event")
            handleEvent(event)
        }
    }

    private fun handleEvent(event: String) {
        val builder = NotificationCompat.Builder(this, Notification.TYPE_REMINDER)
            .setSmallIcon(R.drawable.ic_notification_reminder)
            .setContentTitle("Event Title")
            .setContentText("Event")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(420, builder.build())
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context, event: String): Intent {
            return Intent(context, CreateNotificationService::class.java).apply {
                action = ACTION_VIEW_EVENT
                putExtra("event", event)
            }
        }
    }
}
