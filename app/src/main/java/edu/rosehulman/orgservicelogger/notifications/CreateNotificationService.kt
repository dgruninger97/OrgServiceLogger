package edu.rosehulman.orgservicelogger.notifications

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.rosehulman.orgservicelogger.NoLoginActivity
import edu.rosehulman.orgservicelogger.data.Notification
import edu.rosehulman.orgservicelogger.data.retrieveNotification

class CreateNotificationService : IntentService("ViewEventService") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent?.action == ACTION_VIEW_EVENT) {
            val notificationId = intent.getStringExtra(EXTRA_NOTIFICATION_ID)
            retrieveNotification(notificationId) {
                showNotification(it)
            }
        }
    }

    private fun showNotification(notification: Notification) {
        notification.getTitle(this) { title ->
            notification.getDescription { description ->
                val intent = Intent(this, NoLoginActivity::class.java)
                intent.putExtra("notification", notification.id)
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val builder = NotificationCompat.Builder(this, Notification.TYPE_REMINDER)
                    .setSmallIcon(notification.getIconRes())
                    .setContentTitle(title)
                    .setContentText(description)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                NotificationManagerCompat.from(this).notify(420, builder.build())
            }
        }
    }

    companion object {
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        private const val ACTION_VIEW_EVENT =
            "edu.rosehulman.orgservicelogger.notifications.action.VIEW_EVENT"

        private const val EXTRA_NOTIFICATION_ID = "notification"

        @JvmStatic
        fun createIntent(context: Context, notificationId: String): Intent {
            return Intent(context, CreateNotificationService::class.java).apply {
                action = ACTION_VIEW_EVENT
                putExtra(EXTRA_NOTIFICATION_ID, notificationId)
            }
        }
    }
}
